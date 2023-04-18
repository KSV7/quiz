package com.example.quiz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_session_answer")
public class QuizSessionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_question_id")
    private QuizSessionQuestion quizSessionQuestion;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private QuizAnswer quizAnswer;

    public QuizSessionAnswer() {
    }

    public QuizSessionAnswer(Long id, QuizSessionQuestion quizSessionQuestion, QuizAnswer quizAnswer) {
        this.id = id;
        this.quizSessionQuestion = quizSessionQuestion;
        this.quizAnswer = quizAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizSessionQuestion getQuizSessionQuestion() {
        return quizSessionQuestion;
    }

    public void setQuizSessionQuestion(QuizSessionQuestion quizSessionQuestion) {
        this.quizSessionQuestion = quizSessionQuestion;
    }

    public QuizAnswer getQuizAnswer() {
        return quizAnswer;
    }

    public void setQuizAnswer(QuizAnswer quizAnswer) {
        this.quizAnswer = quizAnswer;
    }
}
