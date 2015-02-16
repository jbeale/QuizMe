package com.quizme.api.service;

import com.quizme.api.model.QuizSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jbeale on 2/7/15.
 */
@Transactional
@Service("sessionService")
public class DefaultSessionService implements SessionService {

    @Override
    public QuizSession getSessionByCode(String code) {
        return null;
    }

    @Override
    public QuizSession getSessionById(int id) {
        return null;
    }

    @Override
    public QuizSession createSession(String title, int ownerId) {
        return null;
    }
}
