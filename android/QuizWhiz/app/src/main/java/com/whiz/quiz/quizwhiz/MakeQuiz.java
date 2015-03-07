package com.whiz.quiz.quizwhiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;
import com.whiz.quiz.quizwhiz.service.RestClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MakeQuiz extends ActionBarActivity {
    Button btnAddQuestion = null;
    Button btnSaveQuiz = null;
    ListView listViewQuestions = null;
    MultipleChoiceQuestionAdapter questionAdapter = null;
    ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions =
            new ArrayList<MultipleChoiceQuestion>();
    final static int REQUEST_CODE = 1;

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
                Intent intent = new Intent(v.getContext(), InputQuestion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivityForResult(intent, REQUEST_CODE);

                /*
                MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                multipleChoiceQuestions.add(multipleChoiceQuestion);
                questionAdapter.notifyDataSetChanged();
                */
            }
        });
/*
        //TODO Make this an actual button
        btnSaveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MultipleChoiceQuestion question : multipleChoiceQuestions){

                }
                //TODO send this thing to Server
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getBundleExtra("multipleChoiceBundle");
                MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
                multipleChoiceQuestion.setQuestionName(bundle.getString("questionName"));
                multipleChoiceQuestion.setQuestion(bundle.getString("question"));
                String[] options = new String[4];
                for(int i=0; i<4; i++){
                    options[i] = bundle.getString("option"+i);
                }
                multipleChoiceQuestion.setPossibleAnswers(options);
                multipleChoiceQuestion.setCorrectAnswerPosition(bundle.getInt("index"));

                multipleChoiceQuestions.add(multipleChoiceQuestion);
                questionAdapter.notifyDataSetChanged();
            }
        }
    }

    public void sendQuestion(String questionName, String questionType, String promptText,
                             String[] optionTexts, int correctOptionIndex){
        new RestClient(getApplicationContext()).get().sendQuestion(questionName, questionType,
                promptText, optionTexts, correctOptionIndex, new Callback<RestResponse<LoginResponseBody>>() {
                    @Override
                    public void success(RestResponse<LoginResponseBody> loginResponseBodyRestResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

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
