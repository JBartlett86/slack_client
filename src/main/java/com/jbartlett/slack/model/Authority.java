package com.jbartlett.slack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class Authority {

    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("team")
    @Expose
    private String team;

    @SerializedName("user")
    @Expose
    private String user;

    @SerializedName("team_id")
    @Expose
    private String teamId;

    @SerializedName("user_id")
    @Expose
    private String userId;

    public boolean isOk() {
        return ok;
    }

    public String getError() {
        return error;
    }

    public String getTeam() {
        return team;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

}
