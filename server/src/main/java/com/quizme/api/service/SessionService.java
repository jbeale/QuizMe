package com.quizme.api.service;

import com.quizme.api.model.QuizSession;

/**
 * Created by jbeale on 2/7/15.
 */
public interface SessionService {
    public QuizSession getSessionByCode(String code);
    public QuizSession getSessionById(int id);
    public QuizSession createSession(String title, int ownerId);

}
