package com.whiz.quiz.quizwhiz.service;

import com.whiz.quiz.quizwhiz.MultipleChoiceQuestion;
import com.whiz.quiz.quizwhiz.model.MultipleChoiceQuestionModel;

/**
 * Created by JohnMain on 3/14/2015.
 */
public class ObjectConverter {

    public static MultipleChoiceQuestion mcConverter(MultipleChoiceQuestionModel model){
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setId(model.getId()); //getting the *question* ID
        question.setQuestion(model.getData().getPrompt());
        question.setQuestionName(model.getName());
        int choiceListLength = model.getData().getChoices().size(); //Accounts for additional choices for future
        String[] choices = new String[choiceListLength];
        for(int i = 0; i < choiceListLength; i++){
            choices[i] = model.getData().getChoices().get(i).getText();

            if (model.getData().getChoices().get(i).getCorrect() == true){
                question.setCorrectAnswerPosition(i);
            }
        }
        question.setPossibleAnswers(choices);

        return question;
    }
}
