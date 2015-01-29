package com.quizme.api.model.response;

import com.google.gson.annotations.Expose;

/**
 * Created by jbeale on 1/29/15.
 */
public class RestResponse {
    @Expose
    public int code;
    @Expose
    public boolean success;
    @Expose
    public Object body;

    public RestResponse(int code, boolean success, Object body) {
        this.code = code;
        this.success = success;
        this.body = body;
    }

    public static RestResponse Build(int code, boolean success, Object body) {
        return new RestResponse(code, success, body);
    }
}
