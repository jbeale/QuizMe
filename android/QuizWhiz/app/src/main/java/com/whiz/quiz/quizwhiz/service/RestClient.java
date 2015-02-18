package com.whiz.quiz.quizwhiz.service;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by jbeale on 2/16/15.
 */
public class RestClient {
    private static Api REST_CLIENT;
    private static String ROOT =
            "http://s-quizme.justinbeale.com/service";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static Api get() { return REST_CLIENT; };

    public static Api set() { return REST_CLIENT; };

    public static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));
         builder.setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(Api.class);
    }
}
