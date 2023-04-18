package com.example.quiz.сontroller;

import com.example.quiz.component.QuizPlayer;
import com.example.quiz.model.*;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.CustomerRepository;
import com.example.quiz.service.SessionService;
import com.example.quiz.service.SuiteThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class QuizPlayerController {
    private final QuizPlayer quizPlayer;
    private final SuiteThemeService suiteThemeService;
    private final CustomerRepository customerRepository;
    private final SessionService sessionService;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuizPlayerController(QuizPlayer quizPlayer,
                                SuiteThemeService suiteThemeService,
                                CustomerRepository customerRepository,
                                SessionService sessionService, AnswerRepository answerRepository) {
        this.quizPlayer = quizPlayer;
        this.suiteThemeService = suiteThemeService;
        this.customerRepository = customerRepository;
        this.sessionService = sessionService;
        this.answerRepository = answerRepository;
    }

    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    public String quizPlayerTestPage(Model model, @ModelAttribute QuizSuite quizSuite) {
        model.addAttribute("suiteThemes", suiteThemeService.findAll());
        return "player_test";
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    public String quizPlayerStart(@PathVariable String name,
                                  Model model,
                                  Principal principal) {
        Customer user = customerRepository.findCustomerByUserName(principal.getName()).orElseThrow();
        if (quizPlayer.getStartFlag()) {
            quizPlayer.start(user.getId(), name);
            quizPlayer.setStartFlag(false);
            model.addAttribute("newTime", quizPlayer.getReportTime());
            QuizQuestion quizQuestion = quizPlayer.loudQuestion();
            getQuizPlayerData(model, quizQuestion);
        } else {
            QuizQuestion quizQuestion = quizPlayer.getCurrentQuestion();
            getQuizPlayerData(model, quizQuestion);
        }
        model.addAttribute("userId", user.getId());
        model.addAttribute("suiteName", name);
        return "quiz_test_page";
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.POST)
    public String quizPlayerNext(Model model,
                                 @RequestParam("user_id") long userId,
                                 @RequestParam("suite_name") String suiteName,
                                 @RequestParam("question_id") long questionId,
                                 @RequestParam("checkAns") int checkAns,
                                 @RequestParam(value = "answer", required=true) List<Long> answerIds) {
        model.addAttribute("userId", userId);
        model.addAttribute("suiteName", suiteName);
        if (quizPlayer.numberQuestions() >= quizPlayer.getCountQuestions()) {
            quizPlayer.saveSessionQuestionAndSessionAnswer(userId, suiteName, questionId, answerIds, checkAns);
            model.addAttribute("countCorrectAnswers", quizPlayer.getCountCorrectAnswers());
            quizPlayer.saveAnswerCorrect(quizPlayer.getCountCorrectAnswers());
            quizPlayer.setStartFlag(true);
            quizPlayer.setNumberQuestions(0);
            return "quiz_test_result_page";
        }
        QuizQuestion quizQuestion = quizPlayer.loudQuestion();
        getQuizPlayerData(model, quizQuestion);
        quizPlayer.saveSessionQuestionAndSessionAnswer(userId, suiteName, questionId, answerIds, checkAns);
        model.addAttribute("newTime", quizPlayer.getReportTime());
        return "quiz_test_page";
    }

    private void getQuizPlayerData(Model model, QuizQuestion quizQuestion) {
        Set<QuizAnswer> questionAnswers = new HashSet<>(quizQuestion.getQuizAnswers());
        if (answerRepository.countCorrectAnswersByQuestionId(quizQuestion.getId()) > 1) {
            model.addAttribute("checkAns", 1);
        } else {
            model.addAttribute("checkAns", 0);
        }
        model.addAttribute("quizPlayerQuestion", quizQuestion);
        model.addAttribute("questionNum", quizPlayer.numberQuestions());
        model.addAttribute("questionTotal", quizPlayer.getCountQuestions());
        model.addAttribute("answers", questionAnswers);
        model.addAttribute("quizSessionId", quizPlayer.getQuizSessionId());
    }

    @RequestMapping(value = "/timeisover/{sessionId}")
    public String timeIsOver(@PathVariable Long sessionId) {
        sessionService.deleteById(sessionId);
        return "time_over";
    }

}