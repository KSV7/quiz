package com.example.quiz.repository;

import com.example.quiz.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<QuizSession, Long> {
//    Optional<QuizSession> findByCustomer_IdAndQuizSuite_Id(Long customerId, Long suiteId);
    List<QuizSession> findByCustomer_Id(Long customerId);
//    List<QuizSession> findAllByCustomer_Id(Long customerId);
}
