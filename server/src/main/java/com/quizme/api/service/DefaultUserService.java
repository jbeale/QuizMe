package com.quizme.api.service;

import com.quizme.api.dao.UserDAO;
import com.quizme.api.model.User;
import com.quizme.api.model.exception.DuplicateUsernameException;
import com.quizme.api.model.request.ApiClientMetadata;
import com.quizme.api.security.TokenGenerator;
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

    @Override
    public String getNewToken(int userId, ApiClientMetadata acm) {
        String token = TokenGenerator.getNewToken();
        userDAO.storeToken(token, userId, acm);

        return token;
    }

    @Override
    public User validateToken(String token) {
        User tokenUser = userDAO.getUserByToken(token, true);
        return tokenUser;
    }

    @Override
    public User addUser(User u, String plainTextPassword) throws DuplicateUsernameException {
        //ensure username not taken
        if (userDAO.findUser(u.getUsername()) != null) {
            throw new DuplicateUsernameException("Username already exists", u.getUsername());
        }
        Hash h = new Sha256Hash(plainTextPassword, new SimpleByteSource("GLOBALSALT"), 100000);
        u.setPassword(h.toHex());
        userDAO.createUser(u);

        return u;
    }
}
