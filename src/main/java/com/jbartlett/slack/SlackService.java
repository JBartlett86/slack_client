package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.Channel;
import com.jbartlett.slack.model.wrappers.ChannelWrapper;
import com.jbartlett.slack.model.wrappers.ChannelsWrapper;
import com.jbartlett.slack.model.wrappers.UserWrapper;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by johnbartlett on 17/08/15.
 */
public interface SlackService {


    @GET("/auth.test")
    public Authority getAuthority();

    @GET("/users.list")
    public UserWrapper getUserList();

    @GET("/channels.list")
    public ChannelsWrapper getChannelList();

    @GET("/channels.info")
    public ChannelWrapper getChannelInfo(@Query("channel") String channel);

    @PUT("/channels.join")
    public ChannelWrapper joinChannel(@Query("name") String channel);

}
