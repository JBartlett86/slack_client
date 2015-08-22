package com.jbartlett.slack;

import com.jbartlett.slack.model.Authority;
import com.jbartlett.slack.model.Channel;
import com.jbartlett.slack.model.User;
import org.junit.Test;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
                return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/getAuthorityResponse.json")));
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
                return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/getUserListResponse.json")));
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
        assertNull(user.getStatus());
        assertEquals("Europe/London", user.getTimezone());
        assertEquals("British Summer Time", user.getTimezoneLabel());
        assertEquals(new Long(3600), user.getTimezoneOffset());
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
        assertFalse(user.isBot());
        assertFalse(user.isHasFiles());
    }

    /**
     * Test users.list api call using retrofit, tests the following;
     *
     * 1) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testGetUserListFail() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("https://slack.com/api/users.list?token=accesstoken", request.getUrl());
                return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:invalid_auth}"));
            }
        });

        // test failures are handled correctly
        Exception e = null;
        try {
            sc.getUserList();
        } catch (Exception ex) {
            e = ex;
        }

        assertNotNull(e);
        assertEquals("invalid_auth", e.getMessage());
    }

    /**
     * Test users.info api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testGetUserInfo() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                // check for valid vs invalid request
                if (request.getUrl().contains("user=testuser")) {
                    assertEquals("https://slack.com/api/users.info?user=testuser&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/getUserInfoResponse.json")));
                } else {
                    assertEquals("https://slack.com/api/users.info?user=&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:no_user}"));
                }

            }
        });

        User user = sc.getUserInfo("testuser");

        // validate individual user attributes mapped correctly
        assertEquals("TESTUSER1", user.getId());
        assertEquals("john", user.getName());
        assertFalse(user.isDeleted());
        assertNull(user.getStatus());
        assertEquals("Europe/London", user.getTimezone());
        assertEquals("British Summer Time", user.getTimezoneLabel());
        assertEquals(new Long(3600), user.getTimezoneOffset());
        assertEquals("4bbe2e", user.getColour());
        assertEquals("John Doe", user.getRealName());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@doe.com", user.getEmail());
        assertEquals(null, user.getSkype());
        assertEquals(null, user.getPhone());
        assertTrue(user.isAdmin());
        assertFalse(user.isOwner());
        assertFalse(user.isPrimaryOwner());
        assertFalse(user.isRestricted());
        assertFalse(user.isUltraRestricted());
        assertFalse(user.isBot());
        assertTrue(user.isHasFiles());

        // test failures are handled correctly
        Exception e = null;
        try {
            sc.getUserInfo("");
        } catch (Exception ex) {
            e = ex;
        }

        assertNotNull(e);
        assertEquals("no_user", e.getMessage());
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
                return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/getChannelListResponse.json")));
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
                return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/getChannelInfoResponse.json")));
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
     * Test channels.create api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testCreateChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.create?name=testchannelfail&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:name_taken}"));
                } else {
                    assertEquals("https://slack.com/api/channels.create?name=testchannel&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/createChannelResponse.json")));
                }
            }
        });

        Channel channel = sc.createChannel("testchannel");
        assertNotNull(channel);
        assertEquals("testchannel", channel.getName());
        assertEquals(1, channel.getMembers().size()); // added creator as member

        Exception e = null;
        try {
            sc.createChannel("testchannelfail");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("name_taken", e.getMessage());

    }

    /**
     * Test channels.archive api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testArchiveChannel() throws Exception {

        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.archive?channel=testchannelfail&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:channel_not_found}"));
                } else {
                    assertEquals("https://slack.com/api/channels.archive?channel=testchannel&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:true}"));
                }
            }


        });

        // test archiving the channel
        sc.archiveChannel("testchannel");

        Exception e = null;
        try {
            sc.archiveChannel("testchannelfail");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("channel_not_found", e.getMessage());
    }

    /**
     * Test channels.archive api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testUnArchiveChannel() throws Exception {

        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.unarchive?channel=testchannelfail&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:channel_not_found}"));
                } else {
                    assertEquals("https://slack.com/api/channels.unarchive?channel=testchannel&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:true}"));
                }
            }


        });

        // test archiving the channel
        sc.unarchiveChannel("testchannel");

        Exception e = null;
        try {
            sc.unarchiveChannel("testchannelfail");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("channel_not_found", e.getMessage());
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
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/joinChannelResponse.json")));
                } else {
                    assertEquals("https://slack.com/api/channels.join?name=&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:no_channel}"));
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

    /**
     * Test channels.kick api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testKickChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.kick?channel=testchannelfail&user=testuser&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:cant_kick_self}"));
                } else {
                    assertEquals("https://slack.com/api/channels.kick?channel=testchannel&user=testuser&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:true}"));
                }
            }
        });

        sc.kickChannel("testchannel", "testuser");

        Exception e = null;
        try {
            sc.kickChannel("testchannelfail", "testuser");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("cant_kick_self", e.getMessage());

    }

    /**
     * Test channels.leave api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testLeaveChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.leave?channel=testchannelfail&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:channel_not_found}"));
                } else {
                    assertEquals("https://slack.com/api/channels.leave?channel=testchannel&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:true}"));
                }
            }
        });

        // test successful response
        sc.leaveChannel("testchannel");

        // test failing response
        Exception e = null;
        try {
            sc.leaveChannel("testchannelfail");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("channel_not_found", e.getMessage());

    }

    /**
     * Test channels.rename api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testRenameChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                if (request.getUrl().contains("testchannelfail")) {
                    assertEquals("https://slack.com/api/channels.rename?channel=testchannelfail&name=newname&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:channel_not_found}"));
                } else {
                    assertEquals("https://slack.com/api/channels.rename?channel=testchannel&name=newname&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/renameChannelResponse.json")));
                }
            }
        });

        // test successful response
        Channel channel = sc.renameChannel("testchannel", "newname");
        assertEquals("newname", channel.getName());


        // test failing response
        Exception e = null;
        try {
            sc.renameChannel("testchannelfail", "newname");
        } catch (Exception exception) {
            e = exception;
        }

        assertNotNull(e);
        assertEquals("channel_not_found", e.getMessage());

    }

    /**
     * Test channels.invite api call using retrofit, tests the following;
     *
     * 1) The correct request format is sent
     * 2) A successful response is correctly handled and returned
     * 3) An invalid request being sent (e.g. no name) is handled as expected
     */
    @Test
    public void testInviteChannel() throws Exception {
        // mock the expected response from the retrofit call and also validate the request format
        SlackClient sc = new SlackClient("accesstoken", new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                // check for valid vs invalid request
                if (request.getUrl().contains("channel=testChannel")) {
                    assertEquals("https://slack.com/api/channels.invite?channel=testChannel&user=testuser&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedFile("application/json", new File("src/test/resources/joinChannelResponse.json")));
                } else {
                    assertEquals("https://slack.com/api/channels.invite?channel=&user=testuser&token=accesstoken", request.getUrl());
                    return new Response("urlhere", 200, "nothing", new ArrayList<Header>(), new TypedString("{ok:false,error:no_channel}"));
                }

            }
        });

        // Test valid invite to a channel
        Channel channel = sc.inviteChannel("testChannel", "testuser");
        assertNotNull(channel);
        assertEquals("testChannel", channel.getName());
        assertEquals(1, channel.getMembers().size());

        // Test inviting to a channel without a channel id being supplied fails and is correctly handled
        Exception e = null;
        try {
            sc.inviteChannel("", "testuser");
        } catch (Exception exception) {
            e = exception;
        }

        // validate the exception
        assertNotNull(e);
        assertEquals("no_channel", e.getMessage());

    }


}