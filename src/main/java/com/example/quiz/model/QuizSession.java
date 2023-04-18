package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_session")
public class QuizSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "suite_id")
    private QuizSuite quizSuite;
    @OneToMany(mappedBy = "quizSession", cascade = CascadeType.ALL)
    private List<QuizSessionQuestion> quizSessionQuestions = new ArrayList<>();

    public QuizSession() {
    }

    public QuizSession(Long id,
                       Customer customer,
                       QuizSuite quizSuite,
                       List<QuizSessionQuestion> quizSessionQuestions) {
        this.id = id;
        this.customer = customer;
        this.quizSuite = quizSuite;
        this.quizSessionQuestions = quizSessionQuestions;
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

    public QuizSuite getQuizSuite() {
        return quizSuite;
    }

    public void setQuizSuite(QuizSuite quizSuite) {
        this.quizSuite = quizSuite;
    }

    public List<QuizSessionQuestion> getQuizSessionQuestions() {
        return quizSessionQuestions;
    }

    public void setQuizSessionQuestions(List<QuizSessionQuestion> quizSessionQuestions) {
        this.quizSessionQuestions = quizSessionQuestions;
    }
}
