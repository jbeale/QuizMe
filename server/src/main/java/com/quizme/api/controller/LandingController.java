package com.quizme.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jbeale on 1/29/15.
 */
@Controller
@Component
@RequestMapping("/")
public class LandingController {
    @RequestMapping("/")
    public String home(Model model) {
        return "noLayout:coming";
    }
}
