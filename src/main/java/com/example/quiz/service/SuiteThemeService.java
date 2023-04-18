package com.example.quiz.service;

import com.example.quiz.model.QuizSuiteTheme;
import com.example.quiz.repository.SuiteThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SuiteThemeService {
    private SuiteThemeRepository repository;

    public SuiteThemeService(SuiteThemeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizSuiteTheme save(QuizSuiteTheme suiteTheme) {
        return repository.save(suiteTheme);
    }

    @Transactional
    public List<QuizSuiteTheme> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizSuiteTheme> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}