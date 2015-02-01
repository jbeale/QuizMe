package com.quizme.api.service;

import com.quizme.api.model.User;
import com.quizme.api.model.request.ApiClientMetadata;

/**
 * Created by jbeale on 1/28/15.
 */
public interface UserService {
    User getCurrentUser();
    User getUser(int userId);
    void createUser(String username, String password, String email);
    String getNewToken(int userId, ApiClientMetadata acm);
}
