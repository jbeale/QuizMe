package com.quizme.api.model.canonical;

import com.google.gson.annotations.Expose;
import com.quizme.api.model.ProblemTypeModel;

import java.util.List;

/**
 * Created by jbeale on 2/26/15.
 */
public class MultipleChoice extends ProblemTypeModel {
    @Expose
    public String prompt;
    @Expose
    public List<Option> choices;

    public String getPrompt() {
        return prompt;
    }

    public List<Option> getChoices() {
        return choices;
    }
}
