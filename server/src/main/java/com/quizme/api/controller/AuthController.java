package com.quizme.api.controller;

import com.quizme.api.model.User;
import com.quizme.api.model.exception.DuplicateUsernameException;
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

        return "redirect:/dashboard";
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String showRegisterForm(Model model) {
        model.addAttribute("titlebar", "Create New Account");
        return "register";
    }
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String plaintextPassword,
                             @RequestParam("firstname") String firstname,
                             @RequestParam("lastname") String lastname,
                             @RequestParam("email") String email,
                             Model model) {
        User u = new User();
        u.setUsername(username);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setEmail(email);

        try {
            userService.addUser(u, plaintextPassword);
        } catch (DuplicateUsernameException e) {
             model.addAttribute("username_error", true);
            model.addAttribute("titlebar", "Create New Account");
            return "register";
        } catch (Exception e) {
             model.addAttribute("register_error", true);
            model.addAttribute("titlebar", "Create New Account");
            return "register";
        }

        //success. log user in and go to home
        UsernamePasswordToken token = new UsernamePasswordToken(username, plaintextPassword, false);

        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {

        }
        return "redirect:/dashboard";

    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
