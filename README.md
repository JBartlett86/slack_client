# Slack Client

A Java based client for [Slack](http://slack.com) that allows simple access to the API utilising the awesome [Retrofit](http://square.github.io/retrofit/).

[![Build Status](https://travis-ci.org/JBartlett86/slack_client.svg?branch=master)](https://travis-ci.org/JohnBartlett/slack_client)
[![codecov.io](http://codecov.io/github/JBartlett86/slack_client/coverage.svg?branch=master)](http://codecov.io/github/JBartlett86/slack_client?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/55d506106dbe17001c0001b2/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55d506106dbe17001c0001b2)

Authentication is required before the client can be used and is performing using OAuth 2.0.

## Accessing the Slack Rest API

The Slack Rest API is accessed by instantiating the SlackClient using a valid access token.

```java
SlackClient slackClient = new SlackClient("Access Token Here");
```

Once a slack client is available the following API methods have currently been implemented.

### [auth.test](https://api.slack.com/methods/auth.test)

This method is used for getting details of the currently authenticated user.

```java
Authority authority = slackClient.getAuthority();
```

### [users.list](https://api.slack.com/methods/users.list)

This method is used for getting details of the users within the team.

```java
List<User> users = slackClient.getUserList();
```

### [users.info](https://api.slack.com/methods/users.info)

This method is used for getting details for an individual user in the team.

```java
User user = slackClient.getUserInfo("User ID Here");
```

### [channels.list](https://api.slack.com/methods/channels.list)

This method is used for getting details of the channels within the team.

```java
List<Channel> channels = slackClient.getChannelList();
```

### [channels.info](https://api.slack.com/methods/channels.info)

This method is used to find out information about a channel.
It provides more details than those contained in the channels.list response.

```java
Channel channel = slackClient.getChannelInfo("Channel ID Here");
```

### [channels.create](https://api.slack.com/methods/channels.create)

This method is used to create a channel.

```java
Channel channel = slackClient.createChannel("Channel Name Here");
```

### [channels.archive](https://api.slack.com/methods/channels.archive)

This method is used to archive a channel.

```java
Channel channel = slackClient.archiveChannel("Channel ID Here");
```

### [channels.unarchive](https://api.slack.com/methods/channels.unarchive)

This method is used to unarchive a channel.

```java
Channel channel = slackClient.unarchiveChannel("Channel ID Here");
```

### [channels.join](https://api.slack.com/methods/channels.list)

This method is used to join a channel. If the channel does not exist, it is created.

```java
Channel channel = slackClient.joinChannel("Channel ID Here");
```

### [channels.kick](https://api.slack.com/methods/channels.kick)

This method is used to kick a user from a channel.

```java
Channel channel = slackClient.kickChannel("Channel ID Here", "User ID to Kick Here");
```

### [channels.leave](https://api.slack.com/methods/channels.leave)

This method is used to leave a channel.

```java
Channel channel = slackClient.leaveChannel("Channel ID Here");
```

### [channels.rename](https://api.slack.com/methods/channels.rename)

This method is used to rename a channel.

```java
Channel channel = slackClient.renameChannel("Channel ID Here", "New Channel Name Here");
```

### [channels.invite](https://api.slack.com/methods/channels.invite)

This method is used to invite a user to a channel.

```java
Channel channel = slackClient.inviteChannel("Channel ID Here", "User ID Here");
```

If an error occurs with any of the API calls an exception is thrown where the error reason is the message of the exception.
