package com.example.quiz.service;

import com.example.quiz.dto.CustomerDTO;
import com.example.quiz.model.Customer;
import com.example.quiz.model.Role;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomerService(CustomerRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    public boolean addUser(String username, String passHash, boolean act) {
        if (repository.existsCustomerByUserName(username))
            return false;
        Customer user = new Customer(username, passHash, act);
        Optional<Role> roleOpt = roleRepository.findById(4L);
        roleOpt.ifPresent(user::addRole);
        repository.save(user);
        return true;
    }

    @Transactional
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void addCustomer(CustomerDTO customerDTO) {
        if (repository.existsByEmail(customerDTO.getEmail())) {
            Customer customer = repository.findCustomerByEmail(customerDTO.getEmail()).orElseThrow();
            CustomerDetails customerDetails = new CustomerDetails(customer);
            Authentication auth = new UsernamePasswordAuthenticationToken(customerDetails,
                    null, customerDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return; // do nothing
        }

        Customer customer = Customer.fromDTO(customerDTO);
        Optional<Role> roleOpt = roleRepository.findById(4L);
        roleOpt.ifPresent(customer::addRole);
        customer.setEnabled(true);
        repository.save(customer);

        CustomerDetails customerDetails = new CustomerDetails(customer);
        Authentication auth = new UsernamePasswordAuthenticationToken(customerDetails,
                null, customerDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}