package com.whiz.quiz.quizwhiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by JohnMain on 3/21/2015.
 */
public class WaitQuiz extends ActionBarActivity {
    Button testButton = null; //TODO delete

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



    }

    //TODO When someone clicks on the back button, ask if they are sure
}
