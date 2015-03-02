package com.whiz.quiz.quizwhiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MakeQuiz extends ActionBarActivity {
    Button btnAddQuestion = null;
    Button btnSaveQuiz = null;
    ListView listViewQuestions = null;
    MultipleChoiceQuestionAdapter questionAdapter = null;
    ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions =
            new ArrayList<MultipleChoiceQuestion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_quiz);

        btnAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        listViewQuestions = (ListView) findViewById(R.id.listViewQuestions);

        questionAdapter = new MultipleChoiceQuestionAdapter(this, multipleChoiceQuestions, this);

        listViewQuestions.setAdapter(questionAdapter);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                multipleChoiceQuestions.add(multipleChoiceQuestion);
                questionAdapter.notifyDataSetChanged();
            }
        });


        //TODO Make this an actual button
        btnSaveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MultipleChoiceQuestion question : multipleChoiceQuestions){


                }
                //TODO send this thing to Server
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_quiz, menu);
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
