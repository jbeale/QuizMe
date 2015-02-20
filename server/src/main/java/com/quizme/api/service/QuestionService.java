package com.quizme.api.service;

import com.quizme.api.model.Question;

import java.util.List;

/**
 * Created by jbeale on 2/20/15.
 */
public interface QuestionService {
    public List<Question> getQuestionsByUser(int userId);
}
