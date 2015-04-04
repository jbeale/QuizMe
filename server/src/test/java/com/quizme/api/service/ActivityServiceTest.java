package com.quizme.api.service;

import com.quizme.api.dao.JdbcActivityDAO;
import com.quizme.api.dao.JdbcQuestionDAO;
import com.quizme.api.model.Activity;
import com.quizme.api.model.ProblemTypeModel;
import com.quizme.api.model.Question;
import com.quizme.api.model.canonical.MultipleChoice;
import com.quizme.api.model.canonical.Option;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jbeale on 3/25/15.
 */
public class ActivityServiceTest {
    @Mock
    public JdbcActivityDAO activityDAO;
    @Mock
    public JdbcQuestionDAO questionDAO;

    DefaultActivityService activityService;

    private Option generateOption(String text, boolean correct) {
        Option o = new Option();
        o.text = text;
        o.correct = correct;
        return o;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefaultActivityService realActivityService = new DefaultActivityService();
        realActivityService.setActivityDAO(activityDAO);
        realActivityService.setQuestionDAO(questionDAO);

        Question mockQuestion = new Question();
        mockQuestion.setAuthorUserId(1);
        mockQuestion.setId(1);
        mockQuestion.setType(Question.TYPE_MULTIPLE_CHOICE);
        MultipleChoice questionData = new MultipleChoice();
        questionData.prompt = "Prompt";
        questionData.choices = new ArrayList<Option>();
        questionData.choices.add(this.generateOption("Test1", false));
        questionData.choices.add(this.generateOption("Test2", false));
        questionData.choices.add(this.generateOption("Test3", false));
        questionData.choices.add(this.generateOption("Test4", true));
        mockQuestion.setData(questionData);

        Activity activity = new Activity();
        activity.setName("Test Activity");
        activity.setQuestionIds("2,3");
        when(activityDAO.getActivity(2)).thenReturn(activity);

        Activity activity2 = new Activity();
        activity2.setName("Test Activity");
        activity2.setQuestionIds("");
        when(activityDAO.getActivity(3)).thenReturn(activity2);

        Activity activity3 = new Activity();
        activity3.setName("Test Activity3");
        activity3.setQuestionIds("21");
        when(activityDAO.getActivity(4)).thenReturn(activity3);

        when(questionDAO.getQuestion(anyInt())).thenReturn(mockQuestion);

        activityService = Mockito.spy(realActivityService);
    }

    @Test
    public void requestingUnknownActivityIdReturnsNull() {
        Activity activity = activityService.getActivity(323);
        assertThat(activity, is(nullValue()));
    }

    @Test
    public void requestingValidActivityIdReturnsActivityObject() {
        Activity returned = activityService.getActivity(2);
        assertThat(returned, is(notNullValue()));
        assertThat(returned.getName(), is("Test Activity"));
    }

    @Test
    public void requestingActivityWithNoItemsShouldReturnEmptyActivity() {
        Activity returned = activityService.getActivity(3);
        assertThat(returned, is(notNullValue()));
        assertThat(returned.getName(), is("Test Activity"));
        assertThat(returned.getQuestions().size(), is(0));
    }

    @Test
    public void requestingActivityWithOneQuestionShouldReturnThatQuestion() {
        Activity returned = activityService.getActivity(4);
        assertThat(returned, is(notNullValue()));
        assertThat(returned.getName(), is("Test Activity3"));
        assertThat(returned.getQuestions().size(), is(1));
        verify(questionDAO, times(1)).getQuestion(21);
    }
    @Test
    public void requestingActivityWithMultipleQuestionsShouldReturnThoseQuestions() {
        Activity returned = activityService.getActivity(2);
        assertThat(returned, is(notNullValue()));
        assertThat(returned.getName(), is("Test Activity"));
        assertThat(returned.getQuestions().size(), is(2));
        verify(questionDAO, times(1)).getQuestion(2);
        verify(questionDAO, times(1)).getQuestion(3);
    }

    @Test
    public void savingAnActivityWithNoIdWillCreateNewActivity() {
        Activity activityToSave = new Activity();
        activityToSave.setName("HELLO!!!!");
        activityToSave.setUserId(2);

        activityService.save(activityToSave);

        verify(activityDAO, times(1)).create(activityToSave);
    }

    @Test
    public void savingAnActivityWithAnIdWillUpdateExistingActivity() {
        Activity activityToSave = new Activity();
        activityToSave.setId(1);
        activityToSave.setName("HELLO!!!!");
        activityToSave.setUserId(2);

        activityService.save(activityToSave);

        verify(activityDAO, times(1)).update(activityToSave);
    }


}
