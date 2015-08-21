package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.wrappers.ChannelWrapper;
import com.jbartlett.slack.model.wrappers.ChannelsWrapper;
import com.jbartlett.slack.model.wrappers.UserWrapper;
import com.jbartlett.slack.model.wrappers.UsersWrapper;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

import java.util.Map;

/**
 * Created by johnbartlett on 17/08/15.
 */
interface SlackService {


    @GET("/auth.test")
    Authority getAuthority();

    @GET("/users.list")
    UsersWrapper getUserList();

    @GET("/users.info")
    UserWrapper getUserInfo(@Query("user") String userId);

    @GET("/channels.list")
    ChannelsWrapper getChannelList();

    @GET("/channels.info")
    ChannelWrapper getChannelInfo(@Query("channel") String channelId);

    @PUT("/channels.create")
    ChannelWrapper createChannel(@Query("name") String channelName);

    @PUT("/channels.archive")
    Map<String, Object> archiveChannel(@Query("channel") String channelId);

    @PUT("/channels.unarchive")
    Map<String, Object> unarchiveChannel(@Query("channel") String channelId);

    @PUT("/channels.join")
    ChannelWrapper joinChannel(@Query("name") String channelName);

    @PUT("/channels.kick")
    Map<String, Object> kickChannel(@Query("channel") String channelId, @Query("user") String userId);

    @PUT("/channels.leave")
    Map<String, Object> leaveChannel(@Query("channel") String channelId);

    @PUT("/channels.rename")
    ChannelWrapper renameChannel(@Query("channel") String channelId, @Query("name") String newChannelName);

    @PUT("/channels.invite")
    ChannelWrapper inviteChannel(@Query("channel") String channelId, @Query("user") String userId);

}
