package com.whiz.quiz.quizwhiz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.whiz.quiz.quizwhiz.EndSessionDialogBox;
import com.whiz.quiz.quizwhiz.QuizWhiz;
import com.whiz.quiz.quizwhiz.SessionKeyDialogBox;
import com.whiz.quiz.quizwhiz.model.client_model.MultipleChoiceQuestion;
import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.model.server_model.ChoiceDataModel;
import com.whiz.quiz.quizwhiz.model.server_model.ChoiceModel;
import com.whiz.quiz.quizwhiz.service.ObjectConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JohnMain on 3/22/2015.
 */
public class TakeQuiz extends Activity {
    ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();

    Activity self;
    Button btnSubmit = null;
    TextView textQuestion = null;
    TextView quesNum = null;
    TextView[] options = new TextView[4];
    int questionCounter = 0;
    Boolean answerSelected = false;
    TextView currentlySelectedAnswer = null;
    Socket mSocket;
    ChoiceDataModel question = null;
    int correctAnswerCount = 0;
    int numQuestions = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        this.self = this;
        mSocket = QuizWhiz.mSocket;

        if(mSocket == null){
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);

            finish();
        }

        mSocket.on("display question", displayQuestion);
        mSocket.on("question closed", questionClosed);
        mSocket.on("reveal correct ans", revealCorrect);
        mSocket.on("end session", endSession);

        btnSubmit = (Button) findViewById(R.id.button_submit);
        textQuestion = (TextView) findViewById(R.id.textViewQuestion);
        quesNum = (TextView) findViewById(R.id.textViewQuesNumber);
        options[0] = (TextView) findViewById(R.id.textOption1);
        options[1] = (TextView) findViewById(R.id.textOption2);
        options[2] = (TextView) findViewById(R.id.textOption3);
        options[3] = (TextView) findViewById(R.id.textOption4);

        numQuestions = getIntent().getIntExtra("numQuestions", 0); // Get number of questions from previous intent
        Boolean isHost = getIntent().getBooleanExtra("isHost", false);
        int questionIndex = getIntent().getIntExtra("questionIndex", 0);
        String questionJson = getIntent().getStringExtra("questionJson");

        JSONObject questionData = null;
        try {
            questionData = new JSONObject(questionJson);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        question = parseQuestion(questionData);
        showQuestion(question);

        //setupDummyList(); //TODO delete
        //nextQuestion(questions.get(questionCounter));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = Integer.parseInt(currentlySelectedAnswer.getTag().toString());
                int correctAnswer = questions.get(questionCounter-1).getCorrectAnswerPosition(); //TODO make the questionCounter-1 more readable

                if (tag == correctAnswer) { //Correct Answer
                    currentlySelectedAnswer.setBackgroundColor(0xFF5CFF58);
                } else //Incorrect answer
                    currentlySelectedAnswer.setBackgroundColor(0xFFFF3B36);

                makeOptionsNotClickable();
                btnSubmit.setClickable(false);


            }
        });

        TextSelectedListener listener = new TextSelectedListener();
        for(int i = 0; i < options.length; i++){
            options[i].setOnClickListener(listener);
        }
    }

    private void showCorrectness(TextView choice, boolean correct) {
        int color = correct ? 0xFF5CFF58 : 0xFFFF3B36;

        choice.setBackgroundColor(color);
    }

    private ChoiceDataModel parseQuestion(JSONObject questionData) {
        ChoiceDataModel choiceData = new ChoiceDataModel();
        try {
            choiceData.setPrompt(questionData.getString("prompt"));
            JSONArray choices = questionData.getJSONArray("choices");
            for (int i = 0; i<choices.length(); i++) {
                JSONObject choicesJSONObject = choices.getJSONObject(i);
                ChoiceModel choiceModel = new ChoiceModel();
                choiceModel.setText(choicesJSONObject.getString("text"));
                choiceModel.setCorrect(choicesJSONObject.getBoolean("correct"));
                choiceData.getChoices().add(choiceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return choiceData;
    }

    private void makeOptionsNotClickable() {
        for (TextView option : options) {
            option.setClickable(false);
        }
    }

    class TextSelectedListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            if(answerSelected == false) {
                v.setBackgroundColor(0xff4b8cff);
                answerSelected = true;
                currentlySelectedAnswer = (TextView) v;
            }
            else{
                resetViews();
                v.setBackgroundColor(0xff4b8cff);
                currentlySelectedAnswer = (TextView) v;
            }
            int currentSelectionIndex = Integer.parseInt((String) v.getTag());
            mSocket.emit("select answer", currentSelectionIndex);
        }
    }

    /*private void nextQuestion(MultipleChoiceQuestion question) {
        //TODO make this stop when all questions are answered and go to -> home or another screen
        textQuestion.setText(question.getQuestion());
        for (int i = 0; i < question.getPossibleAnswers().length; i++){
            options[i].setText(question.getPossibleAnswers()[i]);
        }
        questionCounter++;
        answerSelected = false;
        resetViews();
    }*/
    private void showQuestion(ChoiceDataModel model) {
        textQuestion.setText(model.getPrompt());
        quesNum.setText(questionCounter+"/"+numQuestions);
        for (int i = 0; i < model.getChoices().size(); i++){
            options[i].setText(model.getChoices().get(i).getText());
        }
    }

    private void resetViews() {
        for(int i = 0; i < options.length; i++){
            options[i].setBackgroundColor(0xffecf2ec);
            options[i].setClickable(true);
        }
        btnSubmit.setClickable(true);
        currentlySelectedAnswer = null;
    }

    private Emitter.Listener displayQuestion = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject questionData = (JSONObject)args[0];
            int questionIndex = (Integer)args[1];

            question = parseQuestion(questionData);
            self.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    resetViews();
                    showQuestion(question);
                }
            });
        }
    };

    private Emitter.Listener questionClosed = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(currentlySelectedAnswer != null) {
                int currentSelectionIndex = Integer.parseInt((String) currentlySelectedAnswer.getTag());
                for (int i = 0; i < question.getChoices().size(); i++) {
                    Boolean isCorrect = question.getChoices().get(i).getCorrect();
                    if (isCorrect) {
                        if (i == currentSelectionIndex) {
                            correctAnswerCount++;
                        }
                    }
                }
            }
            self.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                makeOptionsNotClickable();
            }
        });
        }
    };

    private Emitter.Listener revealCorrect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final int correctAnswerIndex = (Integer) args[0];
            self.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(currentlySelectedAnswer != null) {
                        int currentSelectionIndex = Integer.parseInt((String) currentlySelectedAnswer.getTag());
                        showCorrectness(options[correctAnswerIndex], true);
                        if (currentSelectionIndex != correctAnswerIndex)
                            showCorrectness(currentlySelectedAnswer, false);
                    }
                    else
                        showCorrectness(options[correctAnswerIndex], true);


                }
            });
        }
    };

    private Emitter.Listener endSession = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            self.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    double grade =  ((double)correctAnswerCount)/((double)numQuestions) * 100;

                                        

                    EndSessionDialogBox dialogBox = null;
                    dialogBox = new EndSessionDialogBox();
                    dialogBox.setGrade(grade);
                    dialogBox.show(self.getFragmentManager(), "Session Complete");
                }
            });
        }
    };


}
