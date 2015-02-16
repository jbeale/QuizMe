package com.whiz.quiz.quizwhiz.service;

import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by jbeale on 2/16/15.
 */
public interface Api {
    @FormUrlEncoded
    @POST("/auth/login")
    void login(@Field("username") String username, @Field("password") String password, Callback<RestResponse<LoginResponseBody>> callback);

    @FormUrlEncoded
    @POST("/auth/create")
    void register(@Field("username") String username,
                  @Field("password") String password,
                  @Field("firstname") String firstname,
                  @Field("lastname") String lastname,
                  @Field("email") String email, Callback<RestResponse<LoginResponseBody>> callback);
}
