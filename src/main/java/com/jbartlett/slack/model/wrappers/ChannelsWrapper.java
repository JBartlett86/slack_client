package com.jbartlett.slack.model.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbartlett.slack.model.Channel;

import java.util.List;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class ChannelsWrapper {

    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("channels")
    @Expose
    private List<Channel> channels;

    public boolean isOk() {
        return ok;
    }

    public String getError() {
        return error;
    }

    public List<Channel> getChannels() {
        return channels;
    }

}
