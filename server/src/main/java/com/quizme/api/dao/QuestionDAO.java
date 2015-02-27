package com.quizme.api.dao;

import com.quizme.api.model.Question;

import java.util.List;

/**
 * Created by jbeale on 2/19/15.
 */
public interface QuestionDAO {
    public Question getQuestion(int id);
    public int updateQuestion(Question question);
    public int insertQuestion(Question question);
    public void deleteQuestion(int id);
    public List<Question> getQuestionsByUser(int id);
}
