package com.quizme.api.dao;

import com.quizme.api.model.User;

/**
 * Created by jbeale on 1/28/15.
 */
public interface UserDAO {
    public User getUser(int userId);
    public User findUser(String username);
    public void createUser(User u);
}
