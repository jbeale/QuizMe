package com.whiz.quiz.quizwhiz.model.server_model;

/**
 * Created by JohnMain on 3/14/2015.
 */
public class ChoiceModel {
    private String text;
    private Boolean correct;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
