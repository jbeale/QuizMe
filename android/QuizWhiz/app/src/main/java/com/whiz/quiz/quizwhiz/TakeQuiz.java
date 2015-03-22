package com.whiz.quiz.quizwhiz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

/**
 * Created by JohnMain on 3/22/2015.
 */
public class TakeQuiz extends ActionBarActivity {
    ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        setupDummyList();

    }

    private void setupDummyList() {
        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion();
        question1.setCorrectAnswerPosition(1);
        question1.setPossibleAnswers(new String[]{"Hello", "Goodbye", "Test", "More test"});
        question1.set
    }
}
