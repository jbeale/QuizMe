package com.quizme.api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by jbeale on 2/7/15.
 */
public class QuizSession {

    @Expose private int id;
    @Expose private String sessionCode;
    @Expose private String title;
    @Expose private int ownerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }


}
