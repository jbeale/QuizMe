package com.quizme.api.service;

import com.quizme.api.model.User;

/**
 * Created by jbeale on 1/28/15.
 */
public interface UserService {
    User getCurrentUser();
    User getUser(int userId);
    void createUser(String username, String password, String email);
}
