package com.quizme.api.model.request;

/**
 * Created by jbeale on 1/28/15.
 */
public class LoginRequest {
    private String username;
    private String password;
    private boolean remember;

    public LoginRequest(String username, String password, boolean remember){
        this.username = username;
        this.password = password;
        this.remember = remember;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isRemembered() {
        return this.remember;
    }
}
