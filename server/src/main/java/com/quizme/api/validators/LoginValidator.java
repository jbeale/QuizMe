package com.quizme.api.validators;

import com.quizme.api.model.request.LoginRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by jbeale on 3/25/15.
 */
public class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> arg0) {
        return LoginRequest.class.isAssignableFrom(arg0);
    }

    @Override
    public void validate(Object loginRequest, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.mandatory", "Please supply a valid username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.mandatory", "Please provide a valid password");

        if (errors.hasErrors()) return;

        String username = ((LoginRequest)loginRequest).getUsername();
        if (username.length() < 6) {
            errors.rejectValue("username", "username.tooshort", "Username must be at least 6 characters.");
        }

        String password = ((LoginRequest)loginRequest).getPassword();
        if (password.length() < 6) {
            errors.rejectValue("password", "password.tooshort", "Password must be at least 6 characters.");
        }
    }
}
