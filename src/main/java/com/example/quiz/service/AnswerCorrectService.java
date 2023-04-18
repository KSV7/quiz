package com.example.quiz.service;

import com.example.quiz.model.QuizAnswerCorrect;
import com.example.quiz.repository.AnswerCorrectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerCorrectService {
    private final AnswerCorrectRepository repository;

    public AnswerCorrectService(AnswerCorrectRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizAnswerCorrect save(QuizAnswerCorrect answerCorrect) {
        return repository.save(answerCorrect);
    }

    @Transactional
    public List<QuizAnswerCorrect> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizAnswerCorrect> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
