package com.quizme.api.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jbeale on 2/27/15.
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class UnauthorizedResourceAccessException extends Exception {
}
