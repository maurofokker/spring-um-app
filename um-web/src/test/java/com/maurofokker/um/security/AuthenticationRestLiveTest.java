package com.maurofokker.um.security;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.test.common.client.security.ITestAuthenticator;
import com.maurofokker.um.client.UmPaths;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import com.maurofokker.um.util.Um;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ Profiles.CLIENT, Profiles.TEST })
public class AuthenticationRestLiveTest {

    @Autowired
    private UmPaths paths;

    @Autowired
    private ITestAuthenticator auth;

    // tests

    @Test
    public final void whenAuthenticationIsPerformed_then200IsReceived() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).get(paths.getAuthenticationUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenAuthenticationIsPerformed_thenResponseHasContent() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).post(paths.getAuthenticationUri());

        // Then
        assertThat(response.asString(), is(notNullValue()));
    }

    @Test
    public final void whenAuthenticationIsPerformed_thenResponseIsUser() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).get(paths.getAuthenticationUri());

        // Then
        response.as(User.class);
    }

    @Test
    public final void whenAuthenticationIsPerformed_thenUserResponseIsCorrect() {
        // When
        final Response response = givenAuthenticated().contentType(APPLICATION_JSON.toString()).get(paths.getAuthenticationUri());

        // Then
        Assert.assertEquals(new User(Um.EMAIL, Um.PASS, null), response.as(User.class));
    }

    // util

    protected RequestSpecification givenAuthenticated() {
        //return RestAssured.given().auth().preemptive().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
        return auth.givenAuthenticated(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}
