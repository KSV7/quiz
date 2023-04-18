package com.example.quiz.service;

import com.example.quiz.model.QuizSessionAnswer;
import com.example.quiz.repository.SessionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionAnswerService {
    private SessionAnswerRepository repository;

//    @Autowired
    public SessionAnswerService(SessionAnswerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizSessionAnswer save(QuizSessionAnswer quizSessionAnswer) {
        return repository.save(quizSessionAnswer);
    }

    @Transactional
    public List<QuizSessionAnswer> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizSessionAnswer> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
