package com.whiz.quiz.quizwhiz.model.client_model;

/**
 * Created by JohnMain on 2/21/2015.
 */
public class MultipleChoiceQuestion extends QuizQuestion {
    private String[] possibleAnswers = new String[4];
    private int correctAnswerPosition = -1;

    public final static String TYPE = "mc";

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getCorrectAnswerPosition() {
        return correctAnswerPosition;
    }

    public void setCorrectAnswerPosition(int correctAnswerPosition) {
        this.correctAnswerPosition = correctAnswerPosition;
    }
}
