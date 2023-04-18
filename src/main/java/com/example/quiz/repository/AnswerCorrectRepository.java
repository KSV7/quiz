package com.example.quiz.repository;

import com.example.quiz.model.QuizAnswerCorrect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerCorrectRepository extends JpaRepository<QuizAnswerCorrect, Long> {
    Optional<QuizAnswerCorrect> findByQuizSession_Id(Long quizSessionId);
}