package com.example.quiz.config;

import com.example.quiz.service.CustomerDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/favicon.ico",
                        "/tests",
                        "/css/",
                        "/js/",
                        "/static/**",
                        "/login*",
                        "/register*",
                        "/oauth/**")
                .permitAll()
                .requestMatchers("/user_page*",
                        "/test/**").authenticated()
                .requestMatchers(
                        "/quiz_theme/**",
                        "/quiz_suite/**",
                        "/quiz_question/**",
                        "/quiz_answer/**").hasRole("ADMIN")
//                .hasAnyRole("ADMIN","CREATOR","EDITOR","USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user_page", true)
                .and()
                .oauth2Login()
                .loginPage("/login.html")
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        return http.build();
    }
}