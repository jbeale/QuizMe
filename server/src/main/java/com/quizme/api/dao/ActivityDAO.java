package com.quizme.api.dao;

import com.quizme.api.model.Activity;

import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
public interface ActivityDAO {
    public Activity getActivity(int activityId);
    public List<Activity> listActivityByUser(int userId);
    public int create(Activity activity);
    public void update(Activity activity);
    public void delete(int activityId);
}
