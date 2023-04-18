package com.example.quiz.repository;

import com.example.quiz.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionAnswerRepository extends JpaRepository<QuizSessionAnswer, Long> {
    @Query(value="select count(*) " +
            "from (" +
            "select qs.suite_id, qsa.answer_id as user_answered, qsq.question_id" +
            ", (select qa2.id " +
            "     from quiz_suite qs2" +
            "         join quiz_question qq2 on qq2.suite_id = qs2.id" +
            "         join quiz_answer qa2 on qa2.question_id = qq2.id and qa2.is_correct = true" +
            "       where qs2.id = qs.suite_id" +
            "         and qq2.id = qsq.question_id) as correct_answer" +
            "  from quiz_session qs" +
            "  join quiz_session_question qsq on qsq.session_id = qs.id" +
            "  join quiz_session_answer qsa on qsa.session_question_id = qsq.id" +
            " where qs.suite_id = :suiteId and qs.customer_id = :customerId" +
            " ) as t where t.user_answered = correct_answer", nativeQuery = true)
    Integer countQuizSessionCorrectAnswersCount(@Param("suiteId") Long suiteId, @Param("customerId") Long customerId);
}
