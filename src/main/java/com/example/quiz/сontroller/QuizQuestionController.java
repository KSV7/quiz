package com.example.quiz.сontroller;

import com.example.quiz.model.Customer;
import com.example.quiz.model.QuizQuestion;
import com.example.quiz.model.QuizSuite;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.service.QuestionService;
import com.example.quiz.service.SuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/quiz_question")
public class QuizQuestionController {
    private final QuestionService questionService;
    private final SuiteService suiteService;
    private final CustomerRepository customerRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuizQuestionController(QuestionService questionService,
                                  SuiteService suiteService,
                                  CustomerRepository customerRepository,
                                  AnswerRepository answerRepository) {
        this.questionService = questionService;
        this.suiteService = suiteService;
        this.customerRepository = customerRepository;
        this.answerRepository = answerRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String quizQuestionAddPage(@RequestParam("suiteId") long suiteId, Model model) {
        QuizSuite quizSuite = suiteService.findById(suiteId).orElseThrow();
        model.addAttribute("quizSuiteById", quizSuite);
        model.addAttribute("quizSuiteList", suiteService.findAll());
        return "quiz_question_add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveQuizQuestion(@ModelAttribute QuizQuestion quizQuestion,
                                   @RequestParam("suite_id") long suiteId,
                                   Principal principal) {
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizSuite suite = suiteService.findById(suiteId).orElseThrow();
        quizQuestion.setCustomer(user);
        quizQuestion.setQuizSuite(suite);
        questionService.save(quizQuestion);
        return "redirect:/quiz_suite/edit/" + suiteId;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editQuizQuestion(@PathVariable long id, Model model) {
        QuizQuestion quizQuestion = questionService.findById(id).orElseThrow();
        model.addAttribute("quizQuestion", quizQuestion);
        model.addAttribute("quizSuiteList", suiteService.findAll());
        model.addAttribute("quizAnswerList",
                        answerRepository.findQuizAnswersByQuizQuestion_Id(quizQuestion.getId()));
        return "question_editor";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String submitForm(@PathVariable long id,
                             @ModelAttribute QuizQuestion quizQuestion,
                             @RequestParam("suite_id") long suiteId,
                             Principal principal) {
        QuizQuestion questionRep = questionService.findById(id).orElseThrow();
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizSuite suite = suiteService.findById(suiteId).orElseThrow();
        questionRep.setQuestion(quizQuestion.getQuestion());
        questionRep.setCustomer(user);
        questionRep.setQuizSuite(suite);
        questionService.save(questionRep);
        return "redirect:/quiz_suite/edit/" + suiteId;
    }

    @RequestMapping("/delete/{id}")
    public String deleteQuizQuestion(@PathVariable long id) {
        QuizQuestion quizQuestion = questionService.findById(id).orElseThrow();
        questionService.deleteById(id);
        return "redirect:/quiz_suite/edit/" + quizQuestion.getQuizSuite().getId();
    }

}