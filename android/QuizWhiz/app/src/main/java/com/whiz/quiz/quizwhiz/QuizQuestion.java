package com.whiz.quiz.quizwhiz;

/**
 * Created by JohnMain on 2/21/2015.
 */
public abstract class QuizQuestion {
    private String question;
    private String questionName;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
