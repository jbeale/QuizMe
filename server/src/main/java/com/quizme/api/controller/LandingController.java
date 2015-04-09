package com.quizme.api.controller;

import com.quizme.api.model.request.ApiClientMetadata;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jbeale on 1/29/15.
 */
@Controller
@Component
@RequestMapping("/")
public class LandingController {

    @Value("${interactionServer.baseUrl}")
    private String baseUrl;

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("titlebar", "Welcome");
        return "noLayout:index";
    }

    @RequestMapping("/{sessionCode}")
    public String sessionLauncher(Model model, @PathVariable("sessionCode") String sessionCode, HttpServletRequest request) {
        String authToken = "";
        if (userService.getCurrentUser() != null) {
            //generate a token for this user
            //TODO Generate a token when they login on the webapp, and store it in session, so we don't generate so many tokenz
            ApiClientMetadata clientMetadata = new ApiClientMetadata(request.getRemoteAddr(), request.getRemoteHost(), request.getHeader("User-Agent"));
            authToken = userService.getNewToken(userService.getCurrentUser().getId(), clientMetadata);
        }
        model.addAttribute("authToken", authToken);
        model.addAttribute("sessionCode", sessionCode);
       // model.addAttribute("serverUri", "http://take.justinbeale.com");
        model.addAttribute("serverUri", baseUrl);
        model.addAttribute("disableTitlebar", true);
        model.addAttribute("titlebar", "QuizMe Session");
        System.out.println("ISBASE: "+baseUrl);
        return "quiztool";
    }
}
