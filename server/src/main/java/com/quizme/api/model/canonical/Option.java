package com.quizme.api.model.canonical;

import com.google.gson.annotations.Expose;

/**
 * Created by jbeale on 2/26/15.
 */
public class Option {
    @Expose
    public String text;
    @Expose
    public boolean correct;

    public String getText() {
        return text;
    }

    public boolean getCorrect() {
        return correct;
    }
}
