package com.quizme.api.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jbeale on 1/28/15.
 */
public class PageLayoutInterceptor extends HandlerInterceptorAdapter {
    private static final String NO_LAYOUT = "noLayout:";

    private String layoutView;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        if (modelAndView == null) return;
        String originalView = modelAndView.getViewName();

        if (originalView != null && !originalView.startsWith("redirect:")) {
            includeLayout(modelAndView, originalView);
        }
        //this.includeGlobalBreadcrumb(modelAndView);
    }

    private void includeLayout(ModelAndView modelAndView, String originalView) {
        boolean noLayout = originalView.startsWith(NO_LAYOUT);

        String realViewName = (noLayout) ? originalView.substring(NO_LAYOUT.length()) : originalView;

        if (noLayout) {
            modelAndView.setViewName(realViewName);
        } else {
            modelAndView.addObject("view", realViewName);
            modelAndView.setViewName(layoutView);
        }
    }

    /*private void includeGlobalBreadcrumb(ModelAndView mav) {
        List<Space> breadcrumb = new ArrayList<Space>();
        int currentId = 0;

        mav.addObject("g_breadcrumb", breadcrumb);
    }*/

    public void setLayoutView(String layoutView) {
        this.layoutView = layoutView;
    }
}
