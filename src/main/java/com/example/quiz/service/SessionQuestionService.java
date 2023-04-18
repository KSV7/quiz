package com.example.quiz.service;

import com.example.quiz.model.QuizSessionAnswer;
import com.example.quiz.model.QuizSessionQuestion;
import com.example.quiz.repository.SessionQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionQuestionService {
    private SessionQuestionRepository repository;

    public SessionQuestionService(SessionQuestionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizSessionQuestion save(QuizSessionQuestion quizSessionQuestion) {
        return repository.save(quizSessionQuestion);
    }

    @Transactional
    public List<QuizSessionQuestion> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizSessionQuestion> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
