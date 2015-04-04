package com.whiz.quiz.quizwhiz.model.server_model;

import com.whiz.quiz.quizwhiz.model.User;

/**
 * Created by John on 4/4/2015.
 */
public class Session {
    private User ownerUser = null;
    private String sessionName = null;
    private String sessionCode = null;
    private int numQuestions = -1;
    private Boolean isHost = false;

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(Boolean isHost) {
        this.isHost = isHost;
    }
}
