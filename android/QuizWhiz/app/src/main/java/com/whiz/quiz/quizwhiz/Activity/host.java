package com.whiz.quiz.quizwhiz.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.model.client_model.MultipleChoiceQuestion;

import java.util.ArrayList;

public class host extends ActionBarActivity {
    ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();

    Button btnReveal = null;
    Button btnNextQuestion = null;

    TextView textQuestion = null;
    TextView[] options = new TextView[4];
    int questionCounter = 0;
    Boolean answerSelected = false;
    TextView currentlySelectedAnswer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        btnReveal = (Button) findViewById(R.id.button_reveal);
        btnNextQuestion = (Button) findViewById(R.id.button_nextQuestion);
        btnReveal.setVisibility(View.VISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);
        textQuestion = (TextView) findViewById(R.id.textViewQuestion);
        options[0] = (TextView) findViewById(R.id.textOption1);
        options[1] = (TextView) findViewById(R.id.textOption2);
        options[2] = (TextView) findViewById(R.id.textOption3);
        options[3] = (TextView) findViewById(R.id.textOption4);

        setupDummyList(); //TODO delete
        nextQuestion(questions.get(questionCounter));

        btnReveal.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {
                btnReveal.setVisibility(View.GONE);
                btnNextQuestion.setVisibility(View.VISIBLE);

                int tag = Integer.parseInt(currentlySelectedAnswer.getTag().toString());
                int correctAnswer = questions.get(questionCounter-1).getCorrectAnswerPosition(); //TODO make the questionCounter-1 more readable

                if (tag == correctAnswer) {
                    currentlySelectedAnswer.setBackgroundColor(0xFF5CFF58);
                } else
                    currentlySelectedAnswer.setBackgroundColor(0xFFFF3B36);

                makeOptionsNotClickable();
                btnReveal.setClickable(false);

         }
         });

        //TODO text selected party
    }

    private void makeOptionsNotClickable() {
        for (TextView option : options) {
            option.setClickable(false);
        }
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

    private void nextQuestion(MultipleChoiceQuestion question) {
        //TODO make this stop when all questions are answered and go to -> home or another screen
        textQuestion.setText(question.getQuestion());
        for (int i = 0; i < question.getPossibleAnswers().length; i++){
            options[i].setText(question.getPossibleAnswers()[i]);
        }
        questionCounter++;
        answerSelected = false;
        resetViews();
    }

    private void setupDummyList() { //TODO delete
        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion();
        question1.setCorrectAnswerPosition(1);
        question1.setPossibleAnswers(new String[]{"Hello", "Goodbye", "Test", "More test"});
        question1.setQuestionName("I am a question");
        question1.setQuestion("How are you");
        questions.add(question1);

        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion();
        question2.setCorrectAnswerPosition(0);
        question2.setPossibleAnswers(new String[]{"Hellooooooo", "Gooooooodbye", "Test!!!!!", "More test:)))))"});
        question2.setQuestionName("I am a");
        question2.setQuestion("are you good");
        questions.add(question2);

        MultipleChoiceQuestion question3 = new MultipleChoiceQuestion();
        question3.setCorrectAnswerPosition(3);
        question3.setPossibleAnswers(new String[]{"Hellllllllllo", "Goodbyeeeee", "Teeeeest", "Mooooooore test"});
        question3.setQuestionName("I am");
        question3.setQuestion("Hows my hair");
        questions.add(question3);

    }



}
