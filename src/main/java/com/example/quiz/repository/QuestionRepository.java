package com.example.quiz.repository;

import com.example.quiz.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findQuizQuestionsByQuizSuite_Id(Long id);
    Optional<QuizQuestion> findQuizQuestionByIdAndQuizSuite_Id(Long questionId, Long suiteId);
}
