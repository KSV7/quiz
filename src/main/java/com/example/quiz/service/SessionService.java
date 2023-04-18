package com.example.quiz.service;

import com.example.quiz.model.QuizSession;
import com.example.quiz.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizSession save(QuizSession session) {
        return repository.save(session);
    }

    @Transactional
    public List<QuizSession> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizSession> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
