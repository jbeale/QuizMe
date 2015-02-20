package com.whiz.quiz.quizwhiz;

import android.app.Application;

import com.whiz.quiz.quizwhiz.model.User;

/**
 * Created by JohnMain on 2/18/2015.
 */
public class QuizWhiz extends Application {
    private String authToken = null;
    private User user = null;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
