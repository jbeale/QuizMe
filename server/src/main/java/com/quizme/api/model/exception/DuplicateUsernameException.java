package com.quizme.api.model.exception;

/**
 * Created by jbeale on 2/6/15.
 */
public class DuplicateUsernameException extends Exception {
    public String username;

    public DuplicateUsernameException(String msg, String username) {
        super(msg);
        this.username = username;
    }
}
