package com.quizme.api.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
public class Activity {
    @Expose
    private int id;
    @Expose
    private int userId;
    @Expose
    private String name;
    @Expose
    private List<Question> questions;

    private String questionIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds;
    }
}
