package com.example.quiz.config;

import com.example.quiz.dto.CustomerDTO;
import com.example.quiz.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    private final CustomerService customerService;

    public AuthHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        OAuth2User user = token.getPrincipal();

        Map<String, Object> attributes = user.getAttributes();

        CustomerDTO customerDTO = CustomerDTO.of(
                (String) attributes.get("email"),
                (String) attributes.get("name")
        );

        customerService.addCustomer(customerDTO);

        httpServletResponse.sendRedirect("/user_page");
    }
}