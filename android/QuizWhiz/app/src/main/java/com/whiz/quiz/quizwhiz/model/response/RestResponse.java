package com.whiz.quiz.quizwhiz.model.response;

/**
 * Created by jbeale on 2/16/15.
 */
public class RestResponse<T> {
    public Integer code;
    public Boolean success;
    public T body;
}
