package com.whiz.quiz.quizwhiz.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by jbeale on 2/16/15.
 */
public class RestClient {
    private static Api REST_CLIENT;
    private static String ROOT =
            "http://s-quizme.justinbeale.com/service"; //TODO UNSECURE
    private Context context;
    private static SharedPreferences sharedPreferences;

    static {
        setupRestClient();
    }

    public RestClient(){}

    public RestClient(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Api get() { return REST_CLIENT; };

    public static void setupRestClient() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String authToken = sharedPreferences.getString("authToken", "");
                request.addHeader("token", authToken);
            }
        };

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setRequestInterceptor(requestInterceptor);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(Api.class);
    }
}
