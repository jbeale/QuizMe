package com.quizme.api.controller;

import com.quizme.api.model.Activity;
import com.quizme.api.model.User;
import com.quizme.api.service.ActivityService;
import com.quizme.api.service.SessionService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
@Controller
@Component
@RequestMapping("/sessions")
public class SessionsController {

    private ActivityService activityService;
    private UserService userService;
    private SessionService sessionService;

    public boolean checkAuth() {
        //todo use Shiro filters for this
        User user = userService.getCurrentUser();
        return user != null;
    }
    public String redirect() {
        return "redirect:/auth/login";
    }

    @Autowired
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value="/new", method= RequestMethod.GET)
    public String newSessionForm(Model model) {
        if (!checkAuth()) return redirect();
        List<Activity> activities = activityService.listActivitiesByUser(userService.getCurrentUser().getId());
        model.addAttribute("activities", activities);
        model.addAttribute("titlebar", "Start Quiz Session");


        return "sessionlauncher";
    }

    @RequestMapping(value="/new", method=RequestMethod.POST)
    public String startNewSession(Model model) {
        String sessionCode = "370-410-239";
        return "redirect:/"+sessionCode;
    }
}
