package com.example.quiz.service;

import com.example.quiz.model.Customer;
import com.example.quiz.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Customer user = customerRepository.findCustomerByUserName(username).orElseThrow();

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new CustomerDetails(user);
    }
}