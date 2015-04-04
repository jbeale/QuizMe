package com.quizme.api.service;

import com.quizme.api.dao.ActivityDAO;
import com.quizme.api.dao.QuestionDAO;
import com.quizme.api.model.Activity;
import com.quizme.api.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
@Transactional
@Service("activityService")
public class DefaultActivityService implements ActivityService {

    private ActivityDAO activityDAO;
    private QuestionDAO questionDAO;

    @Autowired
    public void setActivityDAO(ActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

    @Autowired
    public void setQuestionDAO(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public Activity getActivity(int id) {
        Activity a = activityDAO.getActivity(id);
        if (a == null) return null;
        String questionIds = a.getQuestionIds();
        String[] itemArray = questionIds.split(",");
        List<Question> items = new ArrayList<Question>();
        if (!questionIds.equals("")) {
            for (int i = 0; i < itemArray.length; i++) {
                Question q = questionDAO.getQuestion(Integer.parseInt(itemArray[i]));
                if (q != null) items.add(q);
            }
        }
        a.setQuestions(items);
        return a;
    }

    @Override
    public List<Activity> listActivitiesByUser(int userId) {
        return activityDAO.listActivityByUser(userId);
    }

    @Override
    public int save(Activity activity) {
        if (activity.getId() != 0) {
            activityDAO.update(activity);
            return activity.getId();
        } else {
            return activityDAO.create(activity);
        }
    }
}
