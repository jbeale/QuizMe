package com.quizme.api.service;

import com.quizme.api.dao.SessionDAO;
import com.quizme.api.dao.UserDAO;
import com.quizme.api.model.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jbeale on 3/25/15.
 */
public class SessionServiceTest {
    @Mock
    private SessionDAO sessionDAO;
    @Mock
    private UserService userService;
    @Mock
    private ActivityService activityService;

    DefaultSessionService sessionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefaultSessionService realSessionService = new DefaultSessionService();
        realSessionService.setActivityService(activityService);
        realSessionService.setUserService(userService);
        realSessionService.setSessionDAO(sessionDAO);
        sessionService = Mockito.spy(realSessionService);
    }

    @Test
    public void gettingSessionByCodeAlsoRetrievesActivityAndOwnerUser() {
        Session fakeSession = new Session();
        fakeSession.setActivityId(1);
        fakeSession.setOwnerUserId(2);
        fakeSession.setSessionName("Test");
        when(sessionDAO.getSessionByCode(anyInt())).thenReturn(fakeSession);
        Session returned = sessionService.getSessionByCode(123456789);
        verify(userService, times(1)).getUser(2);
        verify(activityService, times(1)).getActivity(1);
        assertThat(returned.getSessionName(), is("Test"));
    }

    @Test
    public void gettingSessionByIdAlsoRetrievesActivityAndOwnerUser() {
        Session fakeSession = new Session();
        fakeSession.setActivityId(1);
        fakeSession.setOwnerUserId(2);
        fakeSession.setSessionName("Test");
        when(sessionDAO.getSession(anyInt())).thenReturn(fakeSession);
        Session returned = sessionService.getSessionById(123456789);
        verify(userService, times(1)).getUser(2);
        verify(activityService, times(1)).getActivity(1);
        assertThat(returned.getSessionName(), is("Test"));
    }

    @Test
    public void createSessionGeneratesCorrectSessionObject() {
        when(sessionDAO.newSessionCode()).thenReturn(123456790);
        ArgumentCaptor<Session> argument = ArgumentCaptor.forClass(Session.class);
        sessionService.createSession("My Session Name", 1, 2);
        verify(sessionDAO).createSession(argument.capture());
        Session returned = argument.getValue();
        assertThat(returned.getOwnerUserId(), is(1));
        assertThat(returned.getActivityId(), is(2));
        assertThat(returned.getSessionCode(), is(123456790));
    }
}
