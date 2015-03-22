package com.whiz.quiz.quizwhiz.model.server_model;

import com.whiz.quiz.quizwhiz.model.server_model.ChoiceDataModel;

/**
 * Created by JohnMain on 3/14/2015.
 */
public class MultipleChoiceQuestionModel {
    private int id;
    private int authorUserId;
    private String name;
    private ChoiceDataModel data;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChoiceDataModel getData() {
        return data;
    }

    public void setData(ChoiceDataModel data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
