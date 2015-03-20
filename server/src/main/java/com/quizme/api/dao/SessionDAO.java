package com.quizme.api.dao;

import com.quizme.api.model.Session;

/**
 * Created by jbeale on 3/9/15.
 */
public interface SessionDAO {
    public int createSession(Session session);
    public Session getSession(int sessionId);
    public Session getSessionByCode(int sessionCode);
}
