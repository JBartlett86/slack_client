package com.jbartlett.slack.model.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbartlett.slack.model.Channel;

/**
 * Created by johnbartlett on 18/08/15.
 */
public class ChannelWrapper {

    @SerializedName("ok")
    @Expose
    private boolean ok;

    @SerializedName("already_in_channel")
    @Expose
    private boolean alreadyInChannel;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("channel")
    @Expose
    private Channel channel;

    public boolean isOk() {
        return ok;
    }

    public boolean isAlreadyInChannel() {
        return alreadyInChannel;
    }

    public String getError() {
        return error;
    }

    public Channel getChannel() {
        return channel;
    }

}
