package com.example.quiz.repository;

import com.example.quiz.model.QuizSessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionQuestionRepository extends JpaRepository<QuizSessionQuestion, Long> {
}
