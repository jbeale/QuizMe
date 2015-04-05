package com.whiz.quiz.quizwhiz.model.server_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnMain on 3/14/2015.
 */
public class ChoiceDataModel implements Serializable {
    private String prompt;
    private List<ChoiceModel> choices = new ArrayList<>(); //TODO something wrong with this

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<ChoiceModel> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceModel> choices) {
        this.choices = choices;
    }
}
