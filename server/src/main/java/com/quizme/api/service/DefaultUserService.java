package com.quizme.api.service;

import com.quizme.api.dao.UserDAO;
import com.quizme.api.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jbeale on 1/28/15.
 */
@Transactional
@Service("userService")
public class DefaultUserService implements UserService{

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getCurrentUser() {
        final Integer currentUserId = (Integer) SecurityUtils.getSubject().getPrincipal();
        if (currentUserId != null) {
            return getUser(currentUserId);
        } else {
            return null;
        }
    }

    @Override
    public User getUser(int userId) {
        User u = userDAO.getUser(userId);
        if (u == null) {
            return null;
        }
        return u;
    }

    @Override
    public void createUser(String username, String password, String email) {
        User u = new User();
        u.setUsername(username);
        Hash h = new Sha256Hash(password, new SimpleByteSource("GLOBALSALT"), 100000);
        u.setPassword(h.toHex());
        u.setEmail(email);
        userDAO.createUser(u);
    }
}
