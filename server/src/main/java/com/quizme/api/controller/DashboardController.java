package com.quizme.api.controller;

import com.quizme.api.model.Question;
import com.quizme.api.model.User;
import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by jbeale on 2/19/15.
 */
@Controller
@Component
@RequestMapping("/dashboard")
public class DashboardController {

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


    @RequestMapping("")
    public String dashboard(Model model) {

        model.addAttribute("titlebar", "Temporary User Dashboard");
        return "dashboard";
    }
}
