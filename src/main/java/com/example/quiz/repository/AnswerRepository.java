package com.example.quiz.repository;

import com.example.quiz.model.QuizAnswer;
import com.example.quiz.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<QuizAnswer, Long> {
    List<QuizAnswer> findQuizAnswersByQuizQuestion_Id(Long id);
    @Query("SELECT COUNT(a) FROM QuizAnswer a WHERE a.quizQuestion.id = :questionId AND a.isCorrect = true")
    int countCorrectAnswersByQuestionId(@Param("questionId") Long questionId);
}