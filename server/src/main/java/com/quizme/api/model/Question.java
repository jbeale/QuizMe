package com.quizme.api.model;

import com.google.gson.annotations.Expose;
import org.springframework.beans.factory.parsing.Problem;

/**
 * Created by jbeale on 2/19/15.
 */
public class Question {

    public static final String TYPE_MULTIPLE_CHOICE = "mc";
    public static final String TYPE_FILLIN = "textentry";

    @Expose
    private int id;
    @Expose
    private int authorUserId;
    @Expose
    private Long created;
    @Expose
    private Long modified;
    @Expose
    private String name;
    @Expose
    private ProblemTypeModel data;
    @Expose
    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProblemTypeModel getData() {
        return data;
    }

    public void setData(ProblemTypeModel data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
