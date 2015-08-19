package com.jbartlett.slack;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.testing.auth.oauth2.MockTokenServerTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.testing.json.MockJsonFactory;
import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.Channel;
import com.jbartlett.slack.model.User;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import retrofit.Callback;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.client.UrlConnectionClient;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test to make sure the SlackClient works as expected and creates the expected requests and handles the various response types correctly.
 *
 * Created by johnbartlett on 18/08/15.
 */
public class SlackClientTest {

    /**
     * Test auth.test api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     */
    @Test
    public void testGetAuthority() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("https://slack.com/api/auth.test?token=accesstoken", request.getUrl());
                return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedFile("application/json", new File("src/test/resources/getAuthorityResponse.json")));
            }
        });

        // call and validate the output
        Authority authority = sc.getAuthority();
        assertNotNull(authority);
        assertTrue(authority.isOk());
        assertEquals("TESTTEAMID", authority.getTeamId());
        assertEquals("testteam", authority.getTeam());
        assertEquals("TESTUSER1", authority.getUserId());
        assertEquals("test", authority.getUser());
        assertEquals("https://testteam.slack.com/", authority.getUrl());
    }

    /**
     * Test users.list api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     */
    @Test
    public void testGetUserList() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("https://slack.com/api/users.list?token=accesstoken", request.getUrl());
                return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedFile("application/json", new File("src/test/resources/getUserListResponse.json")));
            }
        });

        // validate the correct number of records have been parsed
        List<User> userList = sc.getUserList();
        assertEquals(2, userList.size());

        Map<String, User> userByName = new HashMap<String, User>();
        for (User user : userList) {
            userByName.put(user.getName(), user);
        }

        // validate properties were mapped as expected
        assertTrue(userByName.containsKey("john"));
        assertTrue(userByName.containsKey("jane"));

        // validate individual user attributes mapped correctly
        User user = userByName.get("john");
        assertEquals("TESTUSER1", user.getId());
        assertEquals("john", user.getName());
        assertFalse(user.isDeleted());
        assertEquals("3c989f", user.getColour());
        assertEquals("John Doe", user.getRealName());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@doe.com", user.getEmail());
        assertEquals(null, user.getSkype());
        assertEquals(null, user.getPhone());
        assertTrue(user.isAdmin());
        assertTrue(user.isOwner());
        assertFalse(user.isPrimaryOwner());
        assertFalse(user.isRestricted());
        assertFalse(user.isUltraRestricted());
    }

    /**
     * Test channels.list api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     */
    @Test
    public void testGetChannelList() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("https://slack.com/api/channels.list?token=accesstoken", request.getUrl());
                return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedFile("application/json", new File("src/test/resources/getChannelListResponse.json")));
            }
        });

        // validate the correct number of records have been parsed
        List<Channel> channelList = sc.getChannelList();
        assertEquals(2, channelList.size());

        Map<String, Channel> channelMap = new HashMap<String, Channel>();
        for (Channel c : channelList) {
            channelMap.put(c.getName(), c);
        }

        // check some properties on the mocked channels
        assertTrue(channelMap.get("general").isMember());
        assertFalse(channelMap.get("android").isMember());
        assertEquals(2, channelMap.get("android").getMembers().size());

        Channel channel = channelMap.get("android");
        assertEquals("CHANNEL1", channel.getId());
        assertEquals("android", channel.getName());
        assertTrue(channel.isChannel());
        assertEquals(new Long("1431248180"), channel.getCreated());
        assertEquals("TESTUSER2", channel.getCreator());
        assertFalse(channel.isArchived());
        assertFalse(channel.isGeneral());
    }

    /**
     * Test channels.info api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     */
    @Test
    public void testGetChannelInfo() throws Exception {

        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("https://slack.com/api/channels.info?channel=testchannel&token=accesstoken", request.getUrl());
                return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedFile("application/json", new File("src/test/resources/getChannelInfoResponse.json")));
            }
        });

        Channel channel = sc.getChannelInfo("testchannel");
        assertNotNull(channel);
        assertEquals("testchannel", channel.getName());
        assertEquals("1420467627.000003", channel.getLastRead());
        assertEquals(new Integer(1), channel.getUnread());
        assertEquals(new Integer(0), channel.getUnreadDisplay());

        // test various values set only within the channels.info method call.
        Channel.Latest latest = channel.getLatest();
        assertNotNull(latest);
        assertEquals("TESTUSER1", latest.getUser());
        assertEquals("message", latest.getType());
        assertEquals("channel_join", latest.getSubType());
        assertEquals("<TESTUSER1|johndoe> has joined the channel", latest.getText());
        assertEquals("1431272775.000002", latest.getTimestamp());

        Channel.ValueHolder topic = channel.getTopic();
        assertNotNull(topic);
        assertEquals("", topic.getValue());
        assertEquals("", topic.getCreator());
        assertEquals(new Integer(0), topic.getLastSet());

        Channel.ValueHolder purpose = channel.getPurpose();
        assertNotNull(purpose);
        assertEquals("This channel is for team-wide communication and announcements. All team members are in this channel.", purpose.getValue());
        assertEquals("", purpose.getCreator());
        assertEquals(new Integer(0), purpose.getLastSet());
    }

    /**
     * Test channels.join api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testJoinChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                // check for valid vs invalid request
                if (request.getUrl().contains("name=testChannel")) {
                    assertEquals("https://slack.com/api/channels.join?name=testChannel&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedFile("application/json", new File("src/test/resources/joinChannelResponse.json")));
                } else {
                    assertEquals("https://slack.com/api/channels.join?name=&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", Collections.EMPTY_LIST, new TypedString("{ok:false,error:no_channel}"));
                }

            }
        });

        // Test valid joining of a channel
        Channel channel = sc.joinChannel("testChannel");
        assertNotNull(channel);
        assertEquals("testChannel", channel.getName());
        assertEquals(1, channel.getMembers().size());

        // Test joining a channel without a name being supplied fails and is correctly handled
        Exception e = null;
        try {
            sc.joinChannel("");
        } catch (Exception exception) {
            e = exception;
        }

        // validate the exception
        assertNotNull(e);
        assertEquals("no_channel", e.getMessage());

    }
}