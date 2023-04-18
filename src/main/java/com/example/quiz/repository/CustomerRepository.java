package com.example.quiz.repository;

import com.example.quiz.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByUserName(String username);
    boolean existsCustomerByUserName(String username);
    boolean existsByEmail(String email);
    Optional<Customer> findCustomerByEmail(String email);
}