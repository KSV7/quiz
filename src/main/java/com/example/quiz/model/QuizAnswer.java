package com.example.quiz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_answer")
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuizQuestion quizQuestion;
    @Column(name = "is_correct")
    private boolean isCorrect;

    public QuizAnswer() {
    }

    public QuizAnswer(Long id,
                      Customer customer,
                      String answer,
                      QuizQuestion quizQuestion,
                      boolean isCorrect) {
        this.id = id;
        this.customer = customer;
        this.answer = answer;
        this.quizQuestion = quizQuestion;
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
