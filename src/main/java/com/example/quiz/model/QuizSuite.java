package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quiz_suite")
public class QuizSuite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private QuizSuiteTheme quizSuiteTheme;
    @OneToMany(mappedBy = "quizSuite", cascade = CascadeType.ALL)
    List<QuizQuestion> quizQuestions;

    public QuizSuite() {
    }

    public QuizSuite(Long id,
                     Customer customer,
                     String name,
                     String description,
                     QuizSuiteTheme quizSuiteTheme,
                     List<QuizQuestion> quizQuestions) {
        this.id = id;
        this.customer = customer;
        this.name = name;
        this.description = description;
        this.quizSuiteTheme = quizSuiteTheme;
        this.quizQuestions = quizQuestions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizSuiteTheme getQuizSuiteTheme() {
        return quizSuiteTheme;
    }

    public void setQuizSuiteTheme(QuizSuiteTheme quizSuiteTheme) {
        this.quizSuiteTheme = quizSuiteTheme;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
}
