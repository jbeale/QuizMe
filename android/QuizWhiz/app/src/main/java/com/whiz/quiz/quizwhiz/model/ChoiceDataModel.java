package com.whiz.quiz.quizwhiz.model;

/**
 * Created by JohnMain on 3/14/2015.
 */
public class ChoiceDataModel {
    private String prompt;
    private ChoiceModel[] choices = new ChoiceModel[4];

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public ChoiceModel[] getChoices() {
        return choices;
    }

    public void setChoices(ChoiceModel[] choices) {
        this.choices = choices;
    }
}
