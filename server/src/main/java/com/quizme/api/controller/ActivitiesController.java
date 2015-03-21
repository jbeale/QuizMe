package com.quizme.api.controller;

import com.quizme.api.model.Activity;
import com.quizme.api.model.Question;
import com.quizme.api.model.User;
import com.quizme.api.model.exception.ResourceNotFoundException;
import com.quizme.api.model.exception.UnauthorizedResourceAccessException;
import com.quizme.api.service.ActivityService;
import com.quizme.api.service.QuestionService;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
@Controller
@Component
@RequestMapping("/activities")
public class ActivitiesController {

    private ActivityService activityService;
    private QuestionService questionService;
    private UserService userService;

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
    @Autowired
    public void setQuestionService(QuestionService questionService) { this.questionService = questionService; }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String listActivities(Model model) {
        if (!checkAuth()) return redirect();
        List<Activity> activityList = activityService.listActivitiesByUser(userService.getCurrentUser().getId());

        model.addAttribute("activities", activityList);
        model.addAttribute("titlebar", "My Activities");
        return "activitylist";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String createActivity(Model model) {
        if (!checkAuth()) return redirect();
        User currentUser = userService.getCurrentUser();

        List<Question> allQuestionsList = questionService.getQuestionsByUser(currentUser.getId());
        model.addAttribute("userQuestionbank", allQuestionsList);
        model.addAttribute("titlebar", "Edit Activity");
        return "activitybuilder";
    }

    @RequestMapping(value="/new", method=RequestMethod.POST)
    public String createActivitySave(@RequestParam("activityName") String name, @RequestParam("questionIds") String questionIds) {
        if (!checkAuth()) return redirect();
        Activity activity = new Activity();
        activity.setName(name);
        activity.setQuestionIds(questionIds);
        activity.setUserId(userService.getCurrentUser().getId());
        activityService.save(activity);
        return "redirect:/activities/list";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String editActivity(@PathVariable("id") int id, Model model) throws UnauthorizedResourceAccessException, ResourceNotFoundException {
        if (!checkAuth()) return redirect();
        Activity activity = activityService.getActivity(id);
        if (activity == null) throw new ResourceNotFoundException();
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId() != activity.getUserId())
            throw new UnauthorizedResourceAccessException();
        List<Question> allQuestionsList = questionService.getQuestionsByUser(currentUser.getId());
        model.addAttribute("activity", activity);
        model.addAttribute("userQuestionbank", allQuestionsList);
        model.addAttribute("titlebar", "Edit Activity");
        return "activitybuilder";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public String editActivitySave(@PathVariable("id") int id, @RequestParam("activityName") String name, @RequestParam("questionIds") String questionIds)
            throws UnauthorizedResourceAccessException, ResourceNotFoundException {
        if (!checkAuth()) return redirect();
        Activity activity = activityService.getActivity(id);
        if (activity == null) throw new ResourceNotFoundException();
        User currentUser = userService.getCurrentUser();
        if (currentUser.getId() != activity.getUserId())
            throw new UnauthorizedResourceAccessException();
        activity.setName(name);
        activity.setQuestionIds(questionIds);
        activityService.save(activity);
        return "redirect:/activities/list";
    }
}
