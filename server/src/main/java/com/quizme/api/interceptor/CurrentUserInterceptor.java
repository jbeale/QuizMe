package com.quizme.api.interceptor;

import com.quizme.api.model.User;
import com.quizme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jbeale on 1/28/15.
 */
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            request.setAttribute("currentUser", currentUser);
        }
    }
}
