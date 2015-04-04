package com.whiz.quiz.quizwhiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.whiz.quiz.quizwhiz.R;

import java.net.URISyntaxException;

/**
 * Created by JohnMain on 3/21/2015.
 */
public class WaitQuiz extends ActionBarActivity {
    Button testButton = null; //TODO delete
    Button testHost = null; //TODO delete this later too!!

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("quizwhiz.justinbeale.com:3001");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_quiz);

        mSocket.on("quiz started", onQuizStarted); //TODO not real name
        mSocket.connect(); //Connect using SocketIO

        testButton = (Button) findViewById(R.id.buttonTest);//TODO Delete
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TakeQuiz.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    private Emitter.Listener onQuizStarted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            int numQuestions = getIntent().getIntExtra("numQuestions", 0); // Get number of questions from previous intent
            Intent intent = new Intent(getApplicationContext(), TakeQuiz.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra("numQuestions", numQuestions);

        }
    };

    //TODO When someone clicks on the back button, ask if they are sure
}
