package com.example.quiz.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quiz_suite_theme")
public class QuizSuiteTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "quizSuiteTheme", cascade = CascadeType.ALL)
    private List<QuizSuite> quizSuites;

    public QuizSuiteTheme() {
    }

    public QuizSuiteTheme(Long id, List<QuizSuite> quizSuites) {
        this.id = id;
        this.quizSuites = quizSuites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuizSuite> getQuizSuites() {
        return quizSuites;
    }

    public void setQuizSuites(List<QuizSuite> quizSuites) {
        this.quizSuites = quizSuites;
    }
}
