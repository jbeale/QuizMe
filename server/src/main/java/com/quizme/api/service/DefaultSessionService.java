package com.quizme.api.service;

import com.quizme.api.dao.SessionDAO;
import com.quizme.api.model.Session;
import com.quizme.api.util.UnixTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jbeale on 2/7/15.
 */
@Transactional
@Service("sessionService")
public class DefaultSessionService implements SessionService {

    private SessionDAO sessionDAO;
    private ActivityService activityService;
    private UserService userService;

    @Autowired
    public void setSessionDAO (SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Autowired
    public void setActivityService (ActivityService activityService) {
        this.activityService = activityService;
    }

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }


    @Override
    public Session getSessionByCode(int code) {
        Session s = sessionDAO.getSessionByCode(code);
        if (s != null) {
            s.setOwnerUser(userService.getUser(s.getOwnerUserId()));
            s.setActivity(activityService.getActivity(s.getActivityId()));
        }
        return s;
    }

    @Override
    public Session getSessionById(int id) {
        Session s = sessionDAO.getSession(id);
        if (s != null) {
            s.setOwnerUser(userService.getUser(s.getOwnerUserId()));
            s.setActivity(activityService.getActivity(s.getActivityId()));
        }
        return s;
    }

    @Override
    public Session createSession(String title, int ownerId, int activityId) {
        Session s = new Session();
        s.setActivityId(activityId);
        s.setCreated(UnixTime.get());
        s.setOwnerUserId(ownerId);
        s.setSessionName(title);
        s.setSessionCode(sessionDAO.newSessionCode());
        int sId = sessionDAO.createSession(s);
        s.setId(sId);
        return s;
    }
}
