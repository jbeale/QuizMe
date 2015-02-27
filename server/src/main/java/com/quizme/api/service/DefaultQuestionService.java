package com.quizme.api.service;

import com.quizme.api.dao.QuestionDAO;
import com.quizme.api.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jbeale on 2/20/15.
 */
@Transactional
@Service("questionService")
public class DefaultQuestionService implements QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    public void setQuestionDAO (QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public List<Question> getQuestionsByUser(int userId) {
        List<Question> questions = questionDAO.getQuestionsByUser(userId);
        return questions;
    }

    @Override
    public Question getQuestion(int questionId) {
        Question q = questionDAO.getQuestion(questionId);
        return q;
    }

    @Override
    public void save(Question q) {
        if (q.getId() != 0) {
            questionDAO.updateQuestion(q);
        } else {
            questionDAO.insertQuestion(q);
        }
    }

    @Override
    public void delete(int questionId) {
        questionDAO.deleteQuestion(questionId);
    }
}
