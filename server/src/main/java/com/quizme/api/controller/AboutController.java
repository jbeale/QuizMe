package com.quizme.api.controller;

import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jbeale on 4/6/15.
 */
@Controller
@Component
@RequestMapping("/about")
public class AboutController {


    @RequestMapping("/tos")
    public String dashboard(Model model) {

        model.addAttribute("titlebar", "Terms of Service");
        return "about";
    }

    @RequestMapping("/credits")
    public String credits(Model model) {
        model.addAttribute("titlebar", "Credits");
        return "credits";
    }
}

