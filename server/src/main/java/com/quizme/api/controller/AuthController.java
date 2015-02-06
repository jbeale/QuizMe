package com.quizme.api.controller;

import com.quizme.api.model.request.LoginRequest;
import com.quizme.api.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by jbeale on 2/6/15.
 */
@Controller
@Component
@RequestMapping("/auth")
public class AuthController {
    protected UserService userService;

    public AuthController() {

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String showLoginForm(Model model) {
        model.addAttribute("titlebar", "Sign In");
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) {

       //TODO form validation
        LoginRequest command = new LoginRequest(username, password, false);

        Errors errors = new BeanPropertyBindingResult(command, "login");

        UsernamePasswordToken token = new UsernamePasswordToken(command.getUsername(),
                command.getPassword(), command.isRemembered());

        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            errors.reject("error.login.generic", e.getMessage());
        }

        if (errors.hasErrors()) {
            model.addAttribute("login_error", true);
            model.addAttribute("titlebar", "Sign In");
            return "login";
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
