package com.example.quiz.service;

import com.example.quiz.model.QuizAnswer;
import com.example.quiz.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository repository;

    public AnswerService(AnswerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizAnswer save(QuizAnswer answer) {
        return repository.save(answer);
    }

    @Transactional
    public List<QuizAnswer> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizAnswer> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
