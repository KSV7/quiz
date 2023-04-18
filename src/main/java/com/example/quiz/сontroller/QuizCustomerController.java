package com.example.quiz.сontroller;

import com.example.quiz.model.Customer;
import com.example.quiz.model.QuizSession;
import com.example.quiz.model.Role;
import com.example.quiz.repository.AnswerCorrectRepository;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.repository.SessionRepository;
import com.example.quiz.service.CustomerDetails;
import com.example.quiz.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.Set;

@Controller
public class QuizCustomerController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final SessionRepository sessionRepository;
    private final AnswerCorrectRepository answerCorrectRepository;

    public QuizCustomerController(CustomerService customerService,
                                  PasswordEncoder passwordEncoder,
                                  CustomerRepository customerRepository,
                                  SessionRepository sessionRepository,
                                  AnswerCorrectRepository answerCorrectRepository) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.sessionRepository = sessionRepository;
        this.answerCorrectRepository = answerCorrectRepository;
    }

    @GetMapping("/login")
    public String logPage() {
        return "security/login";
    }

    @GetMapping("/user_page")
    public String customerPage(Model model, Principal principal) {
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        // Перелік пройдених тестів та кількість правильних відповідей
        HashMap<String,Integer> userTests = new HashMap<>();
        for (QuizSession quizSession: sessionRepository.findByCustomer_Id(user.getId())) {
            userTests.put(quizSession.getQuizSuite().getName(),
                    answerCorrectRepository.findByQuizSession_Id(quizSession.getId()).orElseThrow().getCorrect_answers());
        }
//        model.addAttribute("userRoles", user.getRoles());
        model.addAttribute("userTests", userTests);
        return "security/user_page";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "security/register";
    }

    @PostMapping(value = "/register")
    public String update(@RequestParam String username,
                         @RequestParam String password,
                         Model model) {
        String passHash = passwordEncoder.encode(password);
        if (!customerService.addUser(username, passHash, true)) {
            model.addAttribute("exists", true);
            model.addAttribute("username", username);
            return "security/register";
        }
        return "redirect:/login";
    }
}