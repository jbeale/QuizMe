package com.quizme.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("titlebar", "Welcome");
        return "noLayout:index";
    }

    @RequestMapping("/{sessionCode}")
    public String sessionLauncher(Model model, @PathVariable("sessionCode") String sessionCode) {
        model.addAttribute("sessionCode", sessionCode);
        return "quiztool";
    }
}
