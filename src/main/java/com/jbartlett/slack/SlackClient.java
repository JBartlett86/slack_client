package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.Channel;
import com.jbartlett.slack.model.User;
import com.jbartlett.slack.model.wrappers.ChannelWrapper;
import com.jbartlett.slack.model.wrappers.ChannelsWrapper;
import com.jbartlett.slack.model.wrappers.UserWrapper;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;

import java.util.List;

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
        UserWrapper uw = slackService.getUserList();
        if (!uw.isOk()) {
            throw new Exception(uw.getError());
        }
        return uw.getUsers();
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
     * @param channel The Channel Id
     * @return Channel
     */
    public Channel getChannelInfo(String channel) throws Exception {
        ChannelWrapper cw = slackService.getChannelInfo(channel);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

    /**
     * Gives ability to join a channel with a given name, if channel doesn't exist it will be created.
     * @param channel The channel name
     * @return Channel
     */
    public Channel joinChannel(String channel) throws Exception {
        ChannelWrapper cw = slackService.joinChannel(channel);
        if (!cw.isOk()) {
            throw new Exception(cw.getError());
        }
        return cw.getChannel();
    }

}