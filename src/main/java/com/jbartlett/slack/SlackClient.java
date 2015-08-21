package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.Channel;
import com.jbartlett.slack.model.User;
import com.jbartlett.slack.model.wrappers.ChannelWrapper;
import com.jbartlett.slack.model.wrappers.ChannelsWrapper;
import com.jbartlett.slack.model.wrappers.UserWrapper;
import com.jbartlett.slack.model.wrappers.UsersWrapper;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;

import java.util.List;
import java.util.Map;

import static retrofit.RestAdapter.Builder;
import static retrofit.RestAdapter.LogLevel;

/**
 * A simple, retrofit based client to allow CRUD access to a Slack Team.
 * Utilises OAuth2 to provide suitable authentication before an instance can be supplied for use.
 * <p/>
 * Created by johnbartlett on 17/08/15.
 */
public class SlackClient {

    private SlackService slackService;

    public SlackClient(String accessToken) {
        this(accessToken, LogLevel.BASIC, null);
    }

    public SlackClient(String accessToken, RestAdapter.LogLevel logLevel) {
        this(accessToken, logLevel, null);
    }

    public SlackClient(String accessToken, Client client) {
        this(accessToken, LogLevel.BASIC, client);
    }

    public SlackClient(final String accessToken, RestAdapter.LogLevel logLevel, final Client client) {
        Builder builder = new Builder()
                .setEndpoint("https://slack.com/api/");
        if (client != null) {
            builder.setClient(client);
        }
        builder.setLogLevel(logLevel);
        builder.setRequestInterceptor(new RequestInterceptor() {

            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addQueryParam("token", accessToken);
            }
        });
        RestAdapter restAdapter = builder.build();
        this.slackService = restAdapter.create(SlackService.class);
    }

    /**
     * Get details of the authenticated user
     * @return Authority
     */
    public Authority getAuthority() throws Exception {
        Authority authority = slackService.getAuthority();
        if (!authority.isOk()) {
            throw new Exception(authority.getError());
        }
        return authority;
    }

    /**
     * Get list of users under the current team
     * @return List<User>
     */
    public List<User> getUserList() throws Exception {
        UsersWrapper uw = slackService.getUserList();
        if (!uw.isOk()) {
            throw new Exception(uw.getError());
        }
        return uw.getUsers();
    }

    /**
     * Get user information
     * @return User
     */
    public User getUserInfo(String userId) throws Exception {
        UserWrapper uw = slackService.getUserInfo(userId);
        if (!uw.isOk()) {
            throw new Exception(uw.getError());
        }
        return uw.getUser();
    }

    /**
     * Get list of channels under the current team
     * @return List<Channel>
     */
    public List<Channel> getChannelList() throws Exception {
        ChannelsWrapper cw = slackService.getChannelList();
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannels();
    }

    /**
     * Get more detailed channel information than what is returned from the getChannelList() results
     * @param channelId The Channel Id
     * @return Channel
     */
    public Channel getChannelInfo(String channelId) throws Exception {
        ChannelWrapper cw = slackService.getChannelInfo(channelId);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

    /**
     * Gives ability to create a channel with a given name.
     * @param channelName The channel name
     * @return Channel
     */
    public Channel createChannel(String channelName) throws Exception {
        ChannelWrapper cw = slackService.createChannel(channelName);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

    /**
     * Gives ability to archive a channel with a given Id.
     * @param channelId The channel Id
     */
    public void archiveChannel(String channelId) throws Exception {
        Map<String, Object> response = slackService.archiveChannel(channelId);
        if (!((Boolean)response.get("ok"))) {
            throw new Exception(response.get("error").toString());
        }
    }

    /**
     * Gives ability to unarchive a channel with a given Id.
     * @param channelId The channel Id
     */
    public void unarchiveChannel(String channelId) throws Exception {
        Map<String, Object> response = slackService.unarchiveChannel(channelId);
        if (!((Boolean)response.get("ok"))) {
            throw new Exception(response.get("error").toString());
        }
    }

    /**
     * Gives ability to join a channel with a given name, if channel doesn't exist it will be created.
     * @param channelName The channel name
     * @return Channel
     */
    public Channel joinChannel(String channelName) throws Exception {
        ChannelWrapper cw = slackService.joinChannel(channelName);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

    /**
     * Gives ability to kick a user from a given channel.
     * @param channelId The channel Id
     * @param userId The user Id to Kick
     */
    public void kickChannel(String channelId, String userId) throws Exception {
        Map<String, Object> response = slackService.kickChannel(channelId, userId);
        if (!((Boolean)response.get("ok"))) {
            throw new Exception(response.get("error").toString());
        }
    }

    /**
     * Gives ability to leave a channel
     * @param channelId The channel Id
     */
    public void leaveChannel(String channelId) throws Exception {
        Map<String, Object> response = slackService.leaveChannel(channelId);
        if (!((Boolean)response.get("ok"))) {
            throw new Exception(response.get("error").toString());
        }
    }

    /**
     * Gives ability to rename a channel.
     * @param channelId The channel Id of the channel to rename
     * @param newChannelName The new channel name
     * @return Channel
     */
    public Channel renameChannel(String channelId, String newChannelName) throws Exception {
        ChannelWrapper cw = slackService.renameChannel(channelId, newChannelName);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

}