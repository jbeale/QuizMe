package com.quizme.api.model;

import com.google.gson.annotations.Expose;

/**
 * Created by jbeale on 1/28/15.
 */
public class User {

    @Expose
    protected int id;
    @Expose
    protected String username;
    @Expose
    protected String firstname;
    @Expose
    protected String lastname;
    @Expose
    protected String email;

    protected String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
