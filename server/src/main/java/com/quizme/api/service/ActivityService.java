package com.quizme.api.service;

import com.quizme.api.model.Activity;

import java.util.List;

/**
 * Created by jbeale on 3/6/15.
 */
public interface ActivityService {
    public Activity getActivity(int activityId);
    public List<Activity> listActivitiesByUser(int userId);
    public int save(Activity activity);
}
