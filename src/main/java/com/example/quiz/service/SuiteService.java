package com.example.quiz.service;

import com.example.quiz.model.QuizSuite;
import com.example.quiz.repository.SuiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuiteService {
    private SuiteRepository repository;

    public SuiteService(SuiteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizSuite save(QuizSuite suite) {
        return repository.save(suite);
    }

    @Transactional
    public List<QuizSuite> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizSuite> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
