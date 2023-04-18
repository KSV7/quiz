package com.example.quiz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_answer_correct")
public class QuizAnswerCorrect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "session_id")
    private QuizSession quizSession;
    private Integer correct_answers;

    public QuizAnswerCorrect() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public Integer getCorrect_answers() {
        return correct_answers;
    }

    public void setCorrect_answers(Integer correct_answers) {
        this.correct_answers = correct_answers;
    }
}
