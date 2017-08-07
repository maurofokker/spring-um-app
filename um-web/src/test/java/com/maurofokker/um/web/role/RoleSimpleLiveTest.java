package com.maurofokker.um.web.role;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.common.web.WebConstants;
import com.maurofokker.test.common.util.IDUtil;
import com.maurofokker.um.client.template.RoleSimpleApiClient;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import org.apache.http.HttpHeaders;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@ActiveProfiles({Profiles.CLIENT, Profiles.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleSimpleLiveTest {

    private final static String JSON = MediaType.APPLICATION_JSON.toString();

    @Autowired
    private RoleSimpleApiClient api;

    // find - one

    @Test
    public final void whenNonExistingResourceIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneAsResponse(IDUtil.randomPositiveLong());
    
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(404));
    }

    @Test
    public final void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // When
        final Response res = getApi().read(getUri() + WebConstants.PATH_SEP + randomAlphabetic(6));
    
        // Then
        Assert.assertThat(res.getStatusCode(), CoreMatchers.is(400));
    }
    
    @Test
    public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final String uriForResourseCreation = getApi().createAsResponse(createNewResource()).getHeader(HttpHeaders.LOCATION);
        
        //when
        final Response response = getApi().read(uriForResourseCreation);
    
        // Then
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(200));
    }
    
    @Test
    public final void whenResourceIsCreated_thenResourceIsCorrectlyRetrieved() {
        // Given, When
        final Role newResource = createNewResource();
        final Role createdResource = getApi().create(newResource);

        // Then
        Assert.assertEquals(createdResource, newResource);
    }

    // UTIL

    private final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    private final RoleSimpleApiClient getApi() {
        return api;
    }

    private final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

}