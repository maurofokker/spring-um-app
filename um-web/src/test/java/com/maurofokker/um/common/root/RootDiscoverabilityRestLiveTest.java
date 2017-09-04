package com.maurofokker.um.common.root;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.net.HttpHeaders;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.test.common.client.security.ITestAuthenticator;
import com.maurofokker.test.common.web.util.HTTPLinkHeaderUtil;
import com.maurofokker.um.client.UmPaths;
import com.maurofokker.um.test.live.UmGeneralRestLiveTest;
import com.maurofokker.um.util.Um;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Ignore("temp")
public class RootDiscoverabilityRestLiveTest extends UmGeneralRestLiveTest {

    @Autowired
    private UmPaths paths;
    @Autowired
    private ITestAuthenticator auth;

    // tests

    // GET

    @Test
    public final void whenGetIsDoneOnRoot_thenSomeURIAreDiscoverable() {
        // When
        final Response getOnRootResponse = givenAuthenticated().get(paths.getRootUri());

        // Then
        final List<String> allURIsDiscoverableFromRoot = HTTPLinkHeaderUtil.extractAllURIs(getOnRootResponse.getHeader(HttpHeaders.LINK));

        assertThat(allURIsDiscoverableFromRoot, not(Matchers.<String> empty()));
    }

    @Test
    public final void whenGetIsDoneOnRoot_thenEntityURIIsDiscoverable() {
        // When
        final Response getOnRootResponse = givenAuthenticated().get(paths.getRootUri());

        // Then
        final List<String> allURIsDiscoverableFromRoot = HTTPLinkHeaderUtil.extractAllURIs(getOnRootResponse.getHeader(HttpHeaders.LINK));
        final int indexOfEntityUri = Iterables.indexOf(allURIsDiscoverableFromRoot, Predicates.containsPattern(paths.getUserUri()));
        assertThat(indexOfEntityUri, greaterThanOrEqualTo(0));
    }

    // util

    protected final RequestSpecification givenAuthenticated() {
        return auth.givenAuthenticated(Um.ADMIN_USERNAME, Um.ADMIN_PASS);
    }

}
