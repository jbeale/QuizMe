package com.whiz.quiz.quizwhiz.model.client_model;

/**
 * Created by JohnMain on 2/21/2015.
 */
public abstract class QuizQuestion {
    private String question;
    private String questionName;
    private int id = -1;

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

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
