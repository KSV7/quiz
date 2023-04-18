package com.example.quiz.component;

import com.example.quiz.model.*;
import com.example.quiz.repository.AnswerRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.repository.SuiteRepository;
import com.example.quiz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class QuizPlayer {
    private int numberOfQuestions = 3;
    private int numberQuestions = 0;
    private int countCorrectAnswers = 0;
    private double reportTime;
    private boolean startFlag = true;
    private Deque<QuizQuestion> quizQuestions = new ArrayDeque<>();
    private final CustomerService customerService;
    private final SuiteRepository suiteRepository;
    private final SessionService sessionService;
    private final QuestionRepository questionRepository;
    private final SessionAnswerService sessionAnswerService;
    private final SessionQuestionService sessionQuestionService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private QuizSession quizSession;
    private final AnswerCorrectService answerCorrectService;

    private QuizQuestion currentQuestion;
    private final AnswerRepository answerRepository;
    private int countCorrectAnswersByUser = 0;

    @Autowired
    public QuizPlayer(CustomerService customerService,
                      SuiteRepository suiteRepository,
                      SessionService sessionService,
                      QuestionRepository questionRepository,
                      SessionAnswerService sessionAnswerService,
                      SessionQuestionService sessionQuestionService,
                      QuestionService questionService,
                      AnswerService answerService, AnswerCorrectService answerCorrectService, AnswerRepository answerRepository) {
        this.customerService = customerService;
        this.suiteRepository = suiteRepository;
        this.sessionService = sessionService;
        this.questionRepository = questionRepository;
        this.sessionAnswerService = sessionAnswerService;
        this.sessionQuestionService = sessionQuestionService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerCorrectService = answerCorrectService;
        this.answerRepository = answerRepository;
    }

    public void start(long userId, String suiteName) {
        QuizSession quizSession = new QuizSession();
        // шукаємо та записуємо в quiz_session дані
        Customer customer = customerService.findById(userId).orElseThrow();
        QuizSuite quizSuite = suiteRepository.findQuizSuiteByName(suiteName).orElseThrow();
        quizSession.setCustomer(customer);
        quizSession.setQuizSuite(quizSuite);
        sessionService.save(quizSession);
        this.quizSession = quizSession;
        this.reportTime = System.currentTimeMillis() + (2 * 60 * 1000);

        // Формуємо неповторюючий список запитань
        List<QuizQuestion> quizQuestionsTemp = questionRepository.findQuizQuestionsByQuizSuite_Id(quizSuite.getId());
        Collections.shuffle(quizQuestionsTemp);
        for (QuizQuestion question: quizQuestionsTemp) {
            QuizQuestion quizQuestion = questionRepository
                    .findQuizQuestionByIdAndQuizSuite_Id(question.getId(), quizSuite.getId()).orElseThrow();
            quizQuestions.add(quizQuestion);
        }
    }

    public Long getQuizSessionId() {
        return this.quizSession.getId();
    }

    public QuizQuestion loudQuestion() {
        ++numberQuestions;
        this.currentQuestion = quizQuestions.pop();
        return currentQuestion;
    }

    public QuizQuestion getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }

    public int numberQuestions() {
        return numberQuestions;
    }

    public void setNumberQuestions(int numberQuestions) {
        this.numberQuestions = numberQuestions;
    }

    public double getReportTime() {
        return reportTime;
    }

    public int getCountQuestions() {
        return numberOfQuestions;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void saveSessionQuestionAndSessionAnswer(long customerId,
                                                    String  suiteName,
                                                    long questionId,
                                                    List<Long> answerIds,
                                                    int checkAns) {
//        QuizSuite quizSuite = suiteRepository.findQuizSuiteByName(suiteName).orElseThrow();
//        QuizSession quizSession = sessionRepository.findByCustomer_IdAndQuizSuite_Id(customerId, quizSuite.getId()).orElseThrow();

//        Зберігаемо в БД питання користувача
        QuizSessionQuestion quizSessionQuestion = new QuizSessionQuestion();
        QuizQuestion quizQuestion = questionService.findById(questionId).orElseThrow();
        quizSessionQuestion.setQuizQuestion(quizQuestion);
        quizSessionQuestion.setQuizSession(quizSession);
        sessionQuestionService.save(quizSessionQuestion);

        // Зберігаемо в БД відповідь або відповіді користувача
        if (checkAns == 1) {
//            if (answerIds.size() > 1 && checkAns == 1) {
            for (Long answerId:answerIds) {
                QuizSessionAnswer quizSessionAnswer = new QuizSessionAnswer();
                QuizAnswer quizAnswer = answerService.findById(answerId).orElseThrow();
                quizSessionAnswer.setQuizAnswer(quizAnswer);
                quizSessionAnswer.setQuizSessionQuestion(quizSessionQuestion);
                sessionAnswerService.save(quizSessionAnswer);
                if (quizAnswer.isCorrect()) {
                    ++this.countCorrectAnswersByUser;
                }
            }

            if (answerRepository.countCorrectAnswersByQuestionId(questionId) == this.countCorrectAnswersByUser &&
                    answerRepository.countCorrectAnswersByQuestionId(questionId) == answerIds.size()) {
                ++countCorrectAnswers;
            }

        }

        if (checkAns == 0) {
//            if (answerIds.size() == 1 && checkAns == 0) {
            QuizSessionAnswer quizSessionAnswer = new QuizSessionAnswer();
            QuizAnswer quizAnswer = answerService.findById(answerIds.get(0)).orElseThrow();
            quizSessionAnswer.setQuizAnswer(quizAnswer);
            quizSessionAnswer.setQuizSessionQuestion(quizSessionQuestion);
            sessionAnswerService.save(quizSessionAnswer);
            if (quizAnswer.isCorrect()) {
                ++countCorrectAnswers;
            }
        }

    }
    // Зберігаемо кількість вірних відповідей сесії
    public void saveAnswerCorrect(Integer answerCorrect) {
        QuizAnswerCorrect quizAnswerCorrect = new QuizAnswerCorrect();
        quizAnswerCorrect.setQuizSession(this.quizSession);
        quizAnswerCorrect.setCorrect_answers(answerCorrect);
        answerCorrectService.save(quizAnswerCorrect);
    }
}
