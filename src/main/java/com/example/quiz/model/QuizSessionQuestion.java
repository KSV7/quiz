package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_session_question")
public class QuizSessionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private QuizSession quizSession;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion quizQuestion;
    @OneToMany(mappedBy = "quizSessionQuestion", cascade = CascadeType.ALL)
    private List<QuizSessionAnswer> quizSessionAnswers = new ArrayList<>();

    public QuizSessionQuestion() {
    }

    public QuizSessionQuestion(Long id,
                               QuizSession quizSession,
                               QuizQuestion quizQuestion,
                               List<QuizSessionAnswer> quizSessionAnswers) {
        this.id = id;
        this.quizSession = quizSession;
        this.quizQuestion = quizQuestion;
        this.quizSessionAnswers = quizSessionAnswers;
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

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public List<QuizSessionAnswer> getQuizSessionAnswers() {
        return quizSessionAnswers;
    }

    public void setQuizSessionAnswers(List<QuizSessionAnswer> quizSessionAnswers) {
        this.quizSessionAnswers = quizSessionAnswers;
    }
}
