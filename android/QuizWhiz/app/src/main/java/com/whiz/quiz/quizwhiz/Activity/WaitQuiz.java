package com.whiz.quiz.quizwhiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.whiz.quiz.quizwhiz.R;

/**
 * Created by JohnMain on 3/21/2015.
 */
public class WaitQuiz extends ActionBarActivity {
    Button testButton = null; //TODO delete
    Button testHost = null; //TODO delete this later too!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_quiz);

        testButton = (Button) findViewById(R.id.buttonTest);//TODO Delete
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TakeQuiz.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        testHost = (Button) findViewById(R.id.button_host);
        testHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), host.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

        });




    }

    //TODO When someone clicks on the back button, ask if they are sure
}
