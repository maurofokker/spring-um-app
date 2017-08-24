package com.maurofokker.test.common.client.security;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.OAuthSignature;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.common.client.WebProperties;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Profile("client")
public class OAuthAuthenticator implements ITestAuthenticator {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String CLIENT_ID = "live-test";
    private static final String CLIENT_SECRET = "H0l4MuNd0";

    @Autowired
    private WebProperties webProps;

    public OAuthAuthenticator() {
        super();
    }

    // API

    @Override
    public final RequestSpecification givenAuthenticated(final String username, final String password) {
        final String accessToken = getAccessToken(username, password);
        // config of restassured to consume the Oauth2 secured api
        return RestAssured.given().auth().oauth2(accessToken, OAuthSignature.HEADER);
    }

    final String getAccessToken(final String username, final String password) {
        try {
            // creates the URI (endpoint http://localhost:port/um-web/access/token...)
            final URI uri = new URI(webProps.getProtocol(), null, webProps.getHost(), webProps.getPort(), webProps.getPath() + webProps.getOauthPath(), null, null);
            final String url = uri.toString();
            // for basic auth header to hit token api
            final String encodedCredentials = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));

            // query string to the api to identify the client and generate the token
            final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "password");
            params.add("client_id", CLIENT_ID);
            params.add("username", username);
            params.add("password", password);

            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedCredentials);

            // full request, headers + query string
            final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

            // http://localhost:8086/um-web/oauth/token?grant_type=password&client_id=live-test&username=admin@fake.com&password=adminpass
            // rest template to hit token api with post method
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            final TokenResponse tokenResponse = restTemplate.postForObject(url, request, TokenResponse.class);

            // extracts access token from api response and return it
            final String accessToken = tokenResponse.getAccessToken();
            /*
            response of type
            {
                "access_token": "e36e2740-2431-40f0-a452-eb58cbf9fc58",
                "token_type": "bearer",
                "expires_in": 3599,
                "scope": "um-web"
            }
             */
            return accessToken;
        } catch (final HttpClientErrorException e) {
            log.warn("", e);
            log.info("Full Body = {}", e.getResponseBodyAsString());
        } catch (final URISyntaxException e) {
            log.warn("", e);
        }

        return null;
    }

}
