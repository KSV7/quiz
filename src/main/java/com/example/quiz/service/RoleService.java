package com.example.quiz.service;

import com.example.quiz.model.Role;
import com.example.quiz.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class RoleService {
    private RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Role save(Role role) {
        return repository.save(role);
    }

    @Transactional
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
