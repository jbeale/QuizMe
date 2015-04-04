package com.quizme.api.service;

import com.quizme.api.dao.JdbcQuestionDAO;
import com.quizme.api.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created by jbeale on 3/25/15.
 */
public class QuestionServiceTest {
    @Mock
    public JdbcQuestionDAO questionDAO;

    DefaultQuestionService questionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefaultQuestionService realQuestionService = new DefaultQuestionService();
        realQuestionService.setQuestionDAO(questionDAO);
        questionService = Mockito.spy(realQuestionService);
    }

    @Test
    public void savingAQuestionWithNoIdInsertsNewQuestion() {
        Question q = new Question();
        q.setName("TEST QESTION");

        questionService.save(q);

        verify(questionDAO, times(1)).insertQuestion(q);
    }

    @Test
    public void savingAQuestionWithAnIdUpdatesExistingQuestion() {
        Question q = new Question();
        q.setName("TEST QESTION");
        q.setId(23);

        questionService.save(q);

        verify(questionDAO, times(1)).updateQuestion(q);
    }
}
