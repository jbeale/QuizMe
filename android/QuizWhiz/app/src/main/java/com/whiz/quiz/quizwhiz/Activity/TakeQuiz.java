package com.whiz.quiz.quizwhiz.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whiz.quiz.quizwhiz.model.client_model.MultipleChoiceQuestion;
import com.whiz.quiz.quizwhiz.R;

import java.util.ArrayList;

/**
 * Created by JohnMain on 3/22/2015.
 */
public class TakeQuiz extends ActionBarActivity {
    ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();

    Button btnSubmit = null;
    TextView textQuestion = null;
    TextView[] options = null;
    int questionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        btnSubmit = (Button) findViewById(R.id.button_submit);
        textQuestion = (TextView) findViewById(R.id.textViewQuestion);
        options[0] = (TextView) findViewById(R.id.textOption1);
        options[1] = (TextView) findViewById(R.id.textOption2);
        options[2] = (TextView) findViewById(R.id.textOption3);
        options[3] = (TextView) findViewById(R.id.textOption4);


        setupDummyList();
        nextQuestion(questions.get(questionCounter));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(questions.get(questionCounter));
            }
        });
    }

    private void nextQuestion(MultipleChoiceQuestion question) {
        textQuestion.setText(question.getQuestion());
        for (int i = 0; i < question.getPossibleAnswers().length; i++){
            options[i].setText(question.getPossibleAnswers()[i]);
        }
        questionCounter++;
    }

    private void setupDummyList() {
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
