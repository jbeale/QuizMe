package com.whiz.quiz.quizwhiz.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.SessionKeyDialogBox;

import java.net.URISyntaxException;


public class Home extends ActionBarActivity {
    Button btnMakeQuestions = null;
    Button btnMakeQuiz = null;

    Button btnJoinSession = null;

    //TODO RABIA AND TASMINA what do you want to do about host starting the session? Button?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnMakeQuestions = (Button) findViewById(R.id.buttonMakeQuestions);
        btnMakeQuiz = (Button) findViewById(R.id.buttonMakeQuiz);
        btnJoinSession = (Button) findViewById(R.id.buttonJoinSession);

        btnMakeQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MakeQuestions.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnMakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MakeQuiz.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btnJoinSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new SessionKeyDialogBox().show(getFragmentManager(), "");
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}