package com.quizme.api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by jbeale on 3/9/15.
 */
public class Session {
    @Expose
    private int id;
    @Expose
    private int sessionCode;
    @Expose
    private String sessionName;
    @Expose
    private User ownerUser;
    @Expose
    private int ownerUserId;
    @Expose
    private Activity activity;
    @Expose
    private int activityId;
    @Expose
    private Long created;
    @Expose
    private Long closed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(int sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getClosed() {
        return closed;
    }

    public void setClosed(Long closed) {
        this.closed = closed;
    }
}
