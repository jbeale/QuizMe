package com.whiz.quiz.quizwhiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by JohnMain on 2/6/2015.
 */
public class LoginReciever extends BroadcastReceiver{
    static IntentFilter filter = new IntentFilter("com.whiz.quiz.quizwhiz.AUTHENTICATED");
    Main main = null;

    public LoginReciever(Main _main){
        main = _main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String username = intent.getStringExtra("com.whiz.quiz.quizwhiz.USERNAME");
        String password = intent.getStringExtra("com.whiz.quiz.quizwhiz.PASSWORD");

        main.goToHome(username, password);
    }
}
