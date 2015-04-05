package com.whiz.quiz.quizwhiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.whiz.quiz.quizwhiz.QuizWhiz;
import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.model.server_model.ChoiceDataModel;
import com.whiz.quiz.quizwhiz.model.server_model.ChoiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by JohnMain on 3/21/2015.
 */
public class WaitQuiz extends ActionBarActivity {
    Button testButton = null; //TODO delete
    Button testHost = null; //TODO delete this later too!!
    Socket mSocket = null;
    boolean startedQuiz = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_quiz);

        mSocket = QuizWhiz.mSocket;


        mSocket.on("display question", onQuizStarted); //I think this is the name
    }

    private Emitter.Listener onQuizStarted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (startedQuiz) return;
            startedQuiz = true;
            int numQuestions = getIntent().getIntExtra("numQuestions", 0); // Get number of questions from previous intent
            Boolean isHost = getIntent().getBooleanExtra("isHost", false);

            JSONObject questionData = (JSONObject)args[0];
            int questionIndex = (Integer)args[1];

            /*ChoiceDataModel choiceData = new ChoiceDataModel();
            try {
                choiceData.setPrompt(questionData.getString("prompt"));
                JSONArray choices = questionData.getJSONArray("choices");
                for (int i = 0; i<choices.length(); i++) {
                    JSONObject choicesJSONObject = choices.getJSONObject(i);
                    ChoiceModel choiceModel = new ChoiceModel();
                    choiceModel.setText(choicesJSONObject.getString("text"));
                    choiceData.getChoices().add(choiceModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/


            Intent intent = new Intent(getApplicationContext(), TakeQuiz.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.putExtra("numQuestions", numQuestions);
            intent.putExtra("isHost", isHost);
            intent.putExtra("questionIndex", questionIndex);
            intent.putExtra("questionJson", questionData.toString());
            startActivity(intent);

            finish();
        }
    };

    //TODO When someone clicks on the back button, ask if they are sure
}
