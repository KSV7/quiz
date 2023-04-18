package com.example.quiz.сontroller;

import com.example.quiz.model.QuizSuiteTheme;
import com.example.quiz.repository.SuiteRepository;
import com.example.quiz.service.SuiteThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz_theme")
public class QuizThemeController {
    private final SuiteThemeService suiteThemeService;
    private final SuiteRepository suiteRepository;

    @Autowired
    public QuizThemeController(SuiteThemeService suiteThemeService, SuiteRepository suiteRepository) {
        this.suiteThemeService = suiteThemeService;
        this.suiteRepository = suiteRepository;
    }

    @RequestMapping("/add")
    public String quizThemeAddPage() {
        return "quiz_theme_add";
    }

    @RequestMapping("/quiz_theme_edit")
    public String quizThemeEditPage() {
        return "quiz_theme_edit";
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String quizThemeList(Model model) {
        model.addAttribute("quizSuiteThemes", suiteThemeService.findAll());
        return "quiz_theme_list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveQuizTheme(@ModelAttribute QuizSuiteTheme quizSuiteTheme) {
        suiteThemeService.save(quizSuiteTheme);
        return "redirect:/quiz_theme/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editQuizTheme(@PathVariable long id, Model model) {
        QuizSuiteTheme quizSuiteTheme = suiteThemeService.findById(id).orElseThrow();
        model.addAttribute("quizSuiteTheme", quizSuiteTheme);
        model.addAttribute("quizSuiteList",
                suiteRepository.findQuizSuitesByQuizSuiteTheme_Id(quizSuiteTheme.getId()));
        return "theme_editor";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String submitForm(@PathVariable long id,
                             @ModelAttribute QuizSuiteTheme quizSuiteTheme) {
        QuizSuiteTheme quizSuiteThemeSave = suiteThemeService.findById(id).orElseThrow();
        quizSuiteThemeSave.setName(quizSuiteTheme.getName());
        suiteThemeService.save(quizSuiteThemeSave);
        return "redirect:/quiz_theme/list";
    }

    @RequestMapping("/delete/{id}")
    public String deleteQuizTheme(@PathVariable long id) {
        suiteThemeService.deleteById(id);
        return "redirect:/quiz_theme/list";
    }

}