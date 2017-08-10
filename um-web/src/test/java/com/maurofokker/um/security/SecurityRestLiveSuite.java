package com.maurofokker.um.security;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.um.client.template.UserRestClient;
import com.maurofokker.um.model.UserDtoOpsImpl;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import com.maurofokker.um.util.Um;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mgaldamesc on 10-08-2017.
 */
@ActiveProfiles({Profiles.CLIENT, Profiles.TEST})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        UmLiveTestConfig.class,
        UmClientConfig.class,
        CommonTestConfig.class
}, loader = AnnotationConfigContextLoader.class)
public class SecurityRestLiveSuite {
    @Autowired
    private UserRestClient userClient;

    @Autowired
    private UserDtoOpsImpl userOps;

    // tests

    // Unauthenticated

    @Test
    public final void givenUnauthenticated_whenAResourceIsDeleted_then401IsReceived() {
        // Given
        final String uriOfExistingResource = userClient.createAsUri(userOps.createNewResource());

        // When
        final Response response = given().delete(uriOfExistingResource);

        // Then
        assertThat(response.getStatusCode(), is(401));
    }

    // Authenticated

    @Test
    public final void givenAuthenticatedByBasicAuth_whenResourceIsCreated_then201IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(userClient.getMarshaller().getMime()).body(userOps.createNewResource()).post(userClient.getUri());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    // @Ignore("rest-assured 1.6.2 depends on Jackson 1.x; the new 1.6.3 depends on httpcore and httpclient 4.2.x (which is problematic with Spring)")
    public final void givenAuthenticatedByDigestAuth_whenResourceIsCreated_then201IsReceived() {
        // Given
        // When
        final Response response = givenAuthenticated().contentType(userClient.getMarshaller().getMime()).body(userOps.createNewResource()).post(userClient.getUri());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    // util

    protected final RequestSpecification givenAuthenticated() {
        return RestAssured.given().auth().preemptive().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }
}
