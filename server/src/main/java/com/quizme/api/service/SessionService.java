package com.quizme.api.service;

import com.quizme.api.model.Session;

/**
 * Created by jbeale on 2/7/15.
 */
public interface SessionService {
    public Session getSessionByCode(int code);
    public Session getSessionById(int id);
    public Session createSession(String title, int ownerId, int activityId);

}
