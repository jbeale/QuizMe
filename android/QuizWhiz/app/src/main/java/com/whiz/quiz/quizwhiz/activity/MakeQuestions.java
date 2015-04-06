package com.whiz.quiz.quizwhiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.whiz.quiz.quizwhiz.model.client_model.MultipleChoiceQuestion;
import com.whiz.quiz.quizwhiz.service.MultipleChoiceQuestionAdapter;
import com.whiz.quiz.quizwhiz.model.client_model.QuizQuestion;
import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.model.server_model.MultipleChoiceQuestionModel;
import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;
import com.whiz.quiz.quizwhiz.service.ObjectConverter;
import com.whiz.quiz.quizwhiz.service.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MakeQuestions extends Activity {
    Button btnAddQuestion = null;
    ListView listViewQuestions = null;
    MultipleChoiceQuestionAdapter questionAdapter = null;
    ArrayList<QuizQuestion> questions =
            new ArrayList<QuizQuestion>();
    final static int REQUEST_CODE = 1;
    RestClient restClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_questions);

        btnAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        listViewQuestions = (ListView) findViewById(R.id.listViewQuestions);

        questionAdapter = new MultipleChoiceQuestionAdapter(this, questions, this);

        listViewQuestions.setAdapter(questionAdapter);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputQuestion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        fillInList();
    }

    private void fillInList() {
        RestClient restClient = new RestClient(getApplicationContext());
        restClient.get().getQuestions(new Callback<RestResponse<List<MultipleChoiceQuestionModel>>>() {
            @Override
            public void success(RestResponse<List<MultipleChoiceQuestionModel>> listRestResponse, Response response) {
                ArrayList<MultipleChoiceQuestionModel> arrayList = new ArrayList<>(listRestResponse.body);
                for(int i = 0; i < arrayList.size(); i++) {
                    MultipleChoiceQuestion question = ObjectConverter.mcConverter(arrayList.get(i));
                    questions.add(question);
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Refresh the page or try again later", Toast.LENGTH_SHORT);
            }
        });
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

                sendQuestion(multipleChoiceQuestion);
            }
        }
    }

    public void sendQuestion(final MultipleChoiceQuestion question){
        String questionName = question.getQuestionName();
        String questionType = MultipleChoiceQuestion.TYPE;
        String promptText = question.getQuestion();
        String[] optionTexts = question.getPossibleAnswers();
        int correctOptionIndex = question.getCorrectAnswerPosition();
        restClient = new RestClient(getApplicationContext());
        restClient.get().sendQuestion(questionName, questionType,
                promptText, optionTexts, correctOptionIndex, new Callback<RestResponse<LoginResponseBody>>() {

                    @Override
                    public void success(RestResponse<LoginResponseBody> loginResponseBodyRestResponse, Response response) {
                        questions.add(question);
                        questionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getResponse().getReason().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_questions, menu);
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
