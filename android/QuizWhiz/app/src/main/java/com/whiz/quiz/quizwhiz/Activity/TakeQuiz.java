package com.whiz.quiz.quizwhiz.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.whiz.quiz.quizwhiz.model.client_model.MultipleChoiceQuestion;
import com.whiz.quiz.quizwhiz.R;

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
