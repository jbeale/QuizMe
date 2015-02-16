package com.quizme.api.dao;

import com.quizme.api.model.User;
import com.quizme.api.model.request.ApiClientMetadata;

/**
 * Created by jbeale on 1/28/15.
 */
public interface UserDAO {
    public User getUser(int userId);
    public User findUser(String username);
    public void createUser(User u);
    public void storeToken(String t, int userId, ApiClientMetadata clientMetadata);
    public User getUserByToken(String token, boolean onlyUnexpiredTokens);
}
