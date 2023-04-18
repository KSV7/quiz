package com.example.quiz.сontroller;

import com.example.quiz.model.QuizAnswer;
import com.example.quiz.model.Customer;
import com.example.quiz.model.QuizQuestion;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.service.AnswerService;
import com.example.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/quiz_answer")
public class QuizAnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public QuizAnswerController(QuestionService questionService,
                                AnswerService answerService,
                                CustomerRepository customerRepository) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String quizQuestionAddPage(@RequestParam("questionId") long questionId, Model model) {
        QuizQuestion quizQuestion = questionService.findById(questionId).orElseThrow();
        model.addAttribute("quizQuestionById", quizQuestion);
        model.addAttribute("quizQuestionList", questionService.findAll());
        return "quiz_answer_add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveQuizAnswer(@ModelAttribute QuizAnswer quizAnswer,
                                 @RequestParam("question_id") long questionId,
                                 Principal principal) {
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizQuestion quizQuestion = questionService.findById(questionId).orElseThrow();
        quizAnswer.setCustomer(user);
        quizAnswer.setQuizQuestion(quizQuestion);
        answerService.save(quizAnswer);
        return "redirect:/quiz_question/edit/" + questionId;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editQuizAnswer(@PathVariable long id, Model model) {
        QuizAnswer quizAnswer = answerService.findById(id).orElseThrow();
        model.addAttribute("quizAnswer", quizAnswer);
        model.addAttribute("quizQuestionList", questionService.findAll());
        return "quiz_answer_edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String submitForm(@PathVariable long id,
                             @ModelAttribute QuizAnswer quizAnswer,
                             @RequestParam("question_id") long questionId,
                             Principal principal) {
        QuizAnswer quizAnswerRep = answerService.findById(id).orElseThrow();
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizQuestion quizQuestion = questionService.findById(questionId).orElseThrow();
        quizAnswerRep.setAnswer(quizAnswer.getAnswer());
        quizAnswerRep.setCustomer(user);
        quizAnswerRep.setQuizQuestion(quizQuestion);
        answerService.save(quizAnswerRep);
        return "redirect:/quiz_question/edit/" + questionId;
    }

    @RequestMapping("/delete/{id}")
    public String deleteQuizAnswer(@PathVariable long id) {
        QuizAnswer quizAnswer = answerService.findById(id).orElseThrow();
        System.out.println("*******************************************");
        System.out.println("quizAnswerID: " + id);
        answerService.deleteById(id);
        return "redirect:/quiz_question/edit/" + quizAnswer.getQuizQuestion().getId();
    }

}