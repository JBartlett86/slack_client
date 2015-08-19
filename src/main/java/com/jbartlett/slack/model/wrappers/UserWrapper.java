package com.jbartlett.slack.model.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbartlett.slack.model.User;

import java.util.List;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class UserWrapper {

    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("members")
    @Expose
    private List<User> users;

    public boolean isOk() {
        return ok;
    }

    public String getError() {
        return error;
    }

    public List<User> getUsers() {
        return users;
    }

}
