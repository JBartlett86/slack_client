package com.jbartlett.slack.model.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbartlett.slack.model.User;

/**
 * Created by johnbartlett on 21/08/15.
 */
public class UserWrapper<T> {

    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("user")
    @Expose
    private User user;

    public boolean isOk() {
        return ok;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
