package com.quizme.api.controller;

import com.quizme.api.model.Question;
import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by jbeale on 2/20/15.
 */
@Controller
@Component
@RequestMapping("/questions")
public class QuestionsController {

    private QuestionService questionService;
    private UserService userService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/list")
    public String listMyQuestions(Model model) {
        int userId = userService.getCurrentUser().getId();
        List<Question> questionList = questionService.getQuestionsByUser(userId);

        model.addAttribute("questions", questionList);
        model.addAttribute("titlebar", "My Questions");
        return "questionlist";
    }

    @RequestMapping("/new")
    public String createQuestion(Model model) {
        model.addAttribute("titlebar", "New Question");
        return "questionedit";
    }
}
