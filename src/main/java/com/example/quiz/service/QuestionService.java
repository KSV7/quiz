package com.example.quiz.service;

import com.example.quiz.model.QuizQuestion;
import com.example.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuizQuestion save(QuizQuestion question) {
        return repository.save(question);
    }

    @Transactional
    public List<QuizQuestion> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<QuizQuestion> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
