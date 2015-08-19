# Slack Client

A Java based client for [Slack](http://slack.com) that allows simple access to the API utilising the awesome [Retrofit](http://square.github.io/retrofit/).

[![Build Status](https://travis-ci.org/JBartlett86/slack_client.svg?branch=master)](https://travis-ci.org/JohnBartlett/slack_client)
[![codecov.io](http://codecov.io/github/JBartlett86/slack_client/coverage.svg?branch=master)](http://codecov.io/github/JBartlett86/slack_client?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/55d506106dbe17001c0001b2/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55d506106dbe17001c0001b2)

Authentication is required before the client can be used and is performing using OAuth 2.0.

## Obtaining an authenticated credential

A generic service has been written for obtaining a authenticated credential using the Google Client API.

```java
AuthenticationService authenticationService = new AuthenticationService();
Credential credential = authenticationService.authorise("Token Server URL Here", "Authorisation Server URL Here", "OAuth Key here", "OAuth Secret Here", "Optional Extra Query Args Here");
```

You can then use this authentication mechanism with slack as follows;

```java
AuthenticationService authenticationService = new AuthenticationService();

Map<String, Object> queryArgs = new HashMap<String, Object>();
queryArgs.put("team", "Team ID Here");

Credential credential = authenticationService.authorise("https://slack.com/api/oauth.access", "https://slack.com/oauth/authorize", "OAuth Key here", "OAuth Secret Here", queryArgs);

// now use credential to access the Slack Rest API
```

Once you have an authenticated credential instance you can use it to access the various entities and rich information that Slack
provides for the specified account.

## Accessing the Slack Rest API

The Slack Rest API is accessed by instantiating the SlackClient using a valid access token, this can be retrieved from the Credential returned during authenitciation

```java
SlackClient slackClient = new SlackClient("Access Token Here"); // credential.getAccessToken()
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

### [channels.join](https://api.slack.com/methods/channels.list)

This method is used to join a channel. If the channel does not exist, it is created.

```java
Channel channel = slackClient.joinChannel("Channel ID Here");
```

If an error occurs with any of the API calls an exception is thrown where the error reason is the message of the exception.