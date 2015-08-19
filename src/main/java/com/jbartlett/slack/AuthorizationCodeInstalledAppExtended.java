package com.jbartlett.slack;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;
import java.util.Map;

/**
 * AuthorizationCodeInstalledApp extension that allows additional query arguments to be included.
 *
 * Created by johnbartlett on 18/08/15.
 */
public class AuthorizationCodeInstalledAppExtended extends AuthorizationCodeInstalledApp {

    private Map<String, Object> queryArgs;

    /**
     * @param flow     authorization code flow
     * @param receiver verification code receiver
     * @param queryArgs Any additional query arguements to add to the AuthorizationCodeRequestUrl
     */
    public AuthorizationCodeInstalledAppExtended(AuthorizationCodeFlow flow, VerificationCodeReceiver receiver, Map<String, Object> queryArgs) {
        super(flow, receiver);
        this.queryArgs = queryArgs;
    }

    /**
     * If here are any query arguments available add them to the AuthorizationCodeRequestUrl now.
     */
    @Override
    public void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
        if (queryArgs != null && !queryArgs.isEmpty()) {
            authorizationUrl.putAll(queryArgs);
        }
        super.onAuthorization(authorizationUrl);
    }
}