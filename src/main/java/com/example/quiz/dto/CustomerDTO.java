package com.example.quiz.dto;

public class CustomerDTO {
    private final String email;
    private final String name;

    private CustomerDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static CustomerDTO of(String email, String name) {
        return new CustomerDTO(email, name);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
