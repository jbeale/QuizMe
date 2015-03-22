package com.whiz.quiz.quizwhiz.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.whiz.quiz.quizwhiz.R;

import java.util.ArrayList;


public class MakeQuiz extends ActionBarActivity {

    ListView listChooseQuestions = null;
    ArrayList<String> questionNames = null;
    ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_quiz);

        listChooseQuestions = (ListView) findViewById(R.id.listChooseQuestions);

        questionNames = new ArrayList<>();

        //Test values
        String question1 = "Sample 1";
        String question2 = "Sample 2";
        String question3 = "It works!";

        questionNames.add(question1);
        questionNames.add(question2);
        questionNames.add(question3);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, questionNames);

        listChooseQuestions.setAdapter(adapter);
        listChooseQuestions.setItemsCanFocus(false);
        listChooseQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
