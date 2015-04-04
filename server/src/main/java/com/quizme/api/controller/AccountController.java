package com.quizme.api.controller;

import com.quizme.api.model.User;
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
@RequestMapping("/account")
public class AccountController {

    @Value("${interactionServer.baseUrl}")
    private String baseUrl;

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public boolean checkAuth() {
        //todo use Shiro filters for this
        User user = userService.getCurrentUser();
        return user != null;
    }
    public String redirect() {
        return "redirect:/auth/login";
    }


    @RequestMapping("")
    public String home(Model model) {
        if (!checkAuth()) return redirect();
        model.addAttribute("titlebar", "My Account");
        model.addAttribute("user", userService.getCurrentUser());

        return "account";
    }

}
