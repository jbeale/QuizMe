package com.whiz.quiz.quizwhiz;

import android.app.Application;

/**
 * Created by JohnMain on 2/18/2015.
 */
public class QuizWhiz extends Application {
    private String authToken = null;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
