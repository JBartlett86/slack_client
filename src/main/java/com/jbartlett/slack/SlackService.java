package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.wrappers.ChannelWrapper;
import com.jbartlett.slack.model.wrappers.ChannelsWrapper;
import com.jbartlett.slack.model.wrappers.UserWrapper;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

import java.util.Map;

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
    public ChannelWrapper getChannelInfo(@Query("channel") String channelId);

    @PUT("/channels.create")
    public ChannelWrapper createChannel(@Query("name") String channelName);

    @PUT("/channels.archive")
    public Map<String, Object> archiveChannel(@Query("channel") String channelId);

    @PUT("/channels.unarchive")
    public Map<String, Object> unarchiveChannel(@Query("channel") String channelId);

    @PUT("/channels.join")
    public ChannelWrapper joinChannel(@Query("name") String channelName);

    @PUT("/channels.kick")
    public Map<String, Object> kickChannel(@Query("channel") String channelId, @Query("user") String userId);

    @PUT("/channels.leave")
    public Map<String, Object> leaveChannel(@Query("channel") String channelId);

    @PUT("/channels.rename")
    public ChannelWrapper renameChannel(@Query("channel") String channelId, @Query("name") String newChannelName);

}
