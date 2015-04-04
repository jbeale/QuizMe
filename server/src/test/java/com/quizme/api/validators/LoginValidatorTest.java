package com.quizme.api.validators;

import com.quizme.api.model.request.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by jbeale on 3/25/15.
 */
public class LoginValidatorTest {
    Validator loginValidator;
    Errors e;

    @Before
    public void setup() {
        loginValidator = new LoginValidator();

    }

    @Test
    public void emptyUsernameDoesNotValidate() {
        LoginRequest login = new LoginRequest("", "password", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.getErrorCount(), is(1));
    }

    @Test
    public void validUsernameValidates() {
        LoginRequest login = new LoginRequest("testUsername", "password", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.getErrorCount(), is(0));
    }

    @Test
    public void usernameUnderSixCharactersDoesNotValidate() {
        LoginRequest login = new LoginRequest("12345", "password", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.hasErrors(), is(true));
    }

    @Test
    public void usernameExactlySixCharactersValidates() {
        LoginRequest login = new LoginRequest("123456", "password", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.hasErrors(), is(false));
    }

    @Test
    public void emptyPasswordDoesNotValidate() {
        LoginRequest login = new LoginRequest("123456", "", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.hasErrors(), is(true));
    }

    @Test
    public void passwordUnderEightCharactersDoesNotValidate() {
        LoginRequest login = new LoginRequest("testUsername", "12345", true);
        e = new BeanPropertyBindingResult(login, "login");

        loginValidator.validate(login, e);
        assertThat(e.hasErrors(), is(true));
    }
}
