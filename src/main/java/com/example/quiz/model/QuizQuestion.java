package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quiz_question")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(length = 2048)
    private String question;
    @ManyToOne
    @JoinColumn(name = "suite_id")
    private QuizSuite quizSuite;
    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuizAnswer> quizAnswers;

    public QuizQuestion() {
    }

    public QuizQuestion(Long id,
                        Customer customer,
                        String question,
                        QuizSuite quizSuite,
                        List<QuizAnswer> quizAnswers) {
        this.id = id;
        this.customer = customer;
        this.question = question;
        this.quizSuite = quizSuite;
        this.quizAnswers = quizAnswers;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuizSuite getQuizSuite() {
        return quizSuite;
    }

    public void setQuizSuite(QuizSuite quizSuite) {
        this.quizSuite = quizSuite;
    }

    public List<QuizAnswer> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(List<QuizAnswer> quizAnswers) {
        this.quizAnswers = quizAnswers;
    }
}
