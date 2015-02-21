package com.whiz.quiz.quizwhiz;

/**
 * Created by JohnMain on 2/21/2015.
 */
public class MultipleChoiceQuestion extends QuizQuestion {
    private String[] possibleAnswers = new String[4];

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
