package com.maurofokker.um.web.role;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.um.client.template.RoleSimpleApiClientNoBase;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles({Profiles.CLIENT, Profiles.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleContractLiveTest {

    @Autowired
    private RoleSimpleApiClientNoBase api;

    // create

    @Test
    public final void whenResourceIsCreated_then201IsReceived() throws IOException {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(201));
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    // UTIL

    private final RoleSimpleApiClientNoBase getApi() {
        return api;
    }

    private final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

}