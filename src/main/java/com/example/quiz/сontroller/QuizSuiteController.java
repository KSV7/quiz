package com.example.quiz.сontroller;

import com.example.quiz.model.Customer;
import com.example.quiz.model.QuizSuite;
import com.example.quiz.model.QuizSuiteTheme;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.service.SuiteService;
import com.example.quiz.service.SuiteThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/quiz_suite")
public class QuizSuiteController {
    private final SuiteService suiteService;
    private final SuiteThemeService suiteThemeService;
    private final CustomerRepository customerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizSuiteController(SuiteService suiteService,
                               SuiteThemeService suiteThemeService,
                               CustomerRepository customerRepository,
                               QuestionRepository questionRepository) {
        this.suiteService = suiteService;
        this.suiteThemeService = suiteThemeService;
        this.customerRepository = customerRepository;
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String quizSuiteAddPage(@RequestParam("themeId") long themeId, Model model) {
        QuizSuiteTheme quizSuiteTheme = suiteThemeService.findById(themeId).orElseThrow();
        model.addAttribute("quizThemeById", quizSuiteTheme);
        model.addAttribute("quizThemeList", suiteThemeService.findAll());
        return "quiz_suite_add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveQuizSuite(@ModelAttribute QuizSuite quizSuite,
                                @RequestParam("theme_id") long themeId,
                                Principal principal) {
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizSuiteTheme quizSuiteTheme = suiteThemeService.findById(themeId).orElseThrow();
        quizSuite.setCustomer(user);
        quizSuite.setQuizSuiteTheme(quizSuiteTheme);
        suiteService.save(quizSuite);
        return "redirect:/quiz_theme/edit/" + themeId;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editQuizSuite(@PathVariable long id, Model model) {
        QuizSuite quizSuite = suiteService.findById(id).orElseThrow();
        model.addAttribute("quizSuite", quizSuite);
        model.addAttribute("quizThemeList", suiteThemeService.findAll());
        model.addAttribute("quizQuestionList",
                        questionRepository.findQuizQuestionsByQuizSuite_Id(quizSuite.getId()));
        return "suite_editor";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String submitForm(@PathVariable long id,
                             @ModelAttribute QuizSuite quizSuite,
                             @RequestParam("theme_id") long themeId,
                             Principal principal) {
        QuizSuite quizSuiteSave = suiteService.findById(id).orElseThrow();
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        QuizSuiteTheme quizSuiteTheme = suiteThemeService.findById(themeId).orElseThrow();
        quizSuiteSave.setName(quizSuite.getName());
        quizSuiteSave.setDescription(quizSuite.getDescription());
        quizSuiteSave.setCustomer(user);
        quizSuiteSave.setQuizSuiteTheme(quizSuiteTheme);
        suiteService.save(quizSuiteSave);
        return "redirect:/quiz_theme/edit/" + themeId;
    }

    @RequestMapping("/delete/{id}")
    public String deleteQuizSuite(@PathVariable long id) {
        QuizSuite quizSuite = suiteService.findById(id).orElseThrow();
        suiteService.deleteById(id);
        return "redirect:/quiz_theme/edit/" + quizSuite.getQuizSuiteTheme().getId();
    }

}