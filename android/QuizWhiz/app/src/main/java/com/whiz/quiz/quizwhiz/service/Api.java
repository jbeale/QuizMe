package com.whiz.quiz.quizwhiz.service;


import com.whiz.quiz.quizwhiz.model.MultipleChoiceQuestionModel;
import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;


import com.whiz.quiz.quizwhiz.QuizQuestion;
import com.whiz.quiz.quizwhiz.model.User;
import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by jbeale on 2/16/15.
 */
public interface Api {
    @FormUrlEncoded
    @POST("/auth/login")
    void login(@Field("username") String username,
               @Field("password") String password, Callback<RestResponse<LoginResponseBody>> callback);

    @FormUrlEncoded
    @POST("/auth/create")
    void register(@Field("username") String username,
                  @Field("password") String password,
                  @Field("firstname") String firstname,
                  @Field("lastname") String lastname,
                  @Field("email") String email, Callback<RestResponse<LoginResponseBody>> callback);

    @FormUrlEncoded
    @PUT("/question/new")

    void sendQuestion(
        @Field("questionName") String questionName,
        @Field("questionType") String questionType, //like mc for multiple choice
        @Field("promptText") String promptText,
        @Field("optionTexts") String[] optionTexts,
        @Field("correctOptionIndex") int correctOptionIndex,
        Callback<RestResponse<LoginResponseBody>> callback
     );

    @GET("/question/list") //TODO Not doing this right :(
    void getQuestions(Callback<RestResponse<List<MultipleChoiceQuestionModel>>> callback);

    @FormUrlEncoded
    @POST("/question/{id}")//TODO get id of the question
    void editQuestion(
            @Path("id") int id,
            @Field("questionName") String questionName,
            @Field("questionType") String questionType, //like mc for multiple choice
            @Field("promptText") String promptText,
            @Field("optionTexts") String[] optionTexts,
            @Field("correctOptionIndex") int correctOptionIndex
    );
}
