package com.example.quiz.model;

import com.example.quiz.dto.CustomerDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quiz_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private boolean enabled;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizSuite> quizSuites;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizQuestion> quizQuestions;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizAnswer> quizAnswers;

    public Customer() {
    }

    public Customer(Long id,
                    String userName,
                    String password,
                    String email,
                    String phone,
                    String address,
                    boolean enabled,
                    Set<Role> roles,
                    List<QuizSuite> quizSuites,
                    List<QuizQuestion> quizQuestions,
                    List<QuizAnswer> quizAnswers) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.enabled = enabled;
        this.roles = roles;
        this.quizSuites = quizSuites;
        this.quizQuestions = quizQuestions;
        this.quizAnswers = quizAnswers;
    }

    public Customer(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Customer(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<QuizSuite> getQuizSuites() {
        return quizSuites;
    }

    public void setQuizSuites(List<QuizSuite> quizSuites) {
        this.quizSuites = quizSuites;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public List<QuizAnswer> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(List<QuizAnswer> quizAnswers) {
        this.quizAnswers = quizAnswers;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public static Customer of(String email, String username) {
        return new Customer(username, email);
    }

    public static Customer fromDTO(CustomerDTO customerDTO) {
        return Customer.of(customerDTO.getEmail(), customerDTO.getName());
    }
}
