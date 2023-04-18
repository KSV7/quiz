package com.example.quiz.repository;

import com.example.quiz.model.QuizQuestion;
import com.example.quiz.model.QuizSuite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuiteRepository extends JpaRepository<QuizSuite, Long> {
    List<QuizSuite> findQuizSuitesByQuizSuiteTheme_Id(Long id);
    Optional<QuizSuite> findQuizSuiteByName(String name);
}
