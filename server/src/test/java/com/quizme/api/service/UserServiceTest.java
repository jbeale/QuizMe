package com.quizme.api.service;

import com.quizme.api.dao.JdbcUserDAO;
import com.quizme.api.model.User;
import com.quizme.api.model.exception.DuplicateUsernameException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jbeale on 3/25/15.
 */
public class UserServiceTest {
    @Mock
    public JdbcUserDAO userDAO;

    DefaultUserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefaultUserService realUserService = new DefaultUserService();
        realUserService.setUserDAO(userDAO);
        userService = Mockito.spy(realUserService);
    }

    @Test
    public void createUserBuildsCorrectUserObject() {
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        userService.createUser("username", "password", "email@test.com");
        verify(userDAO).createUser(argument.capture());
        User u = argument.getValue();
        assertThat(u.getUsername(), is("username"));
        assertThat(u.getPassword(), is(not("password"))); //Should be a Sha256 hash of the password!
        assertThat(u.getEmail(), is("email@test.com"));
    }

    @Test(expected = DuplicateUsernameException.class)
    public void addUserThrowsDuplicateUsernameExceptionIfUsernameTaken() throws DuplicateUsernameException {
        when(userDAO.findUser("justin")).thenReturn(new User());
        User u = new User();
        u.setUsername("justin");
        userService.addUser(u, "password");
    }

    @Test
    public void addUserThrowsNoExceptionIfNoDuplicateUsername() {
        when(userDAO.findUser(anyString())).thenReturn(null);
        User u = new User();
        u.setUsername("justin");
        try {
            userService.addUser(u, "password");
        } catch (DuplicateUsernameException e) {
            fail();
        }
    }
}
