package com.whiz.quiz.quizwhiz.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.whiz.quiz.quizwhiz.R;

public class host extends ActionBarActivity {

    Button btnReveal = null;
    Button btnNextQuestion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        btnReveal = (Button) findViewById(R.id.button_reveal);
        btnNextQuestion = (Button) findViewById(R.id.button_nextQuestion);
            btnReveal.setVisibility(View.VISIBLE);
            btnNextQuestion.setVisibility(View.INVISIBLE);
            btnReveal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnReveal.setVisibility(View.GONE);
                    btnNextQuestion.setVisibility(View.VISIBLE);
                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host, menu);
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
