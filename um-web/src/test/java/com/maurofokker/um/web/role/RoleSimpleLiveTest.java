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

import java.util.Collection;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@ActiveProfiles({Profiles.CLIENT, Profiles.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public final class RoleSimpleLiveTest extends GenericSimpleLiveTest<Role> {

    @Autowired
    private RoleSimpleApiClient api;

    /*@Override
    @Test
    @Ignore("operation not supported")
    public final void whenResourceIsCreated_then201IsReceived() {

    }*/

    // UTIL

    @Override
    protected final RoleSimpleApiClient getApi() {
        return api;
    }

    @Override
    protected final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

    @Override
    protected Collection<Privilege> getAssociations(Role resource) {
        return resource.getPrivileges();
    }

    @Override
    protected Privilege createNewAssociationResource() {
        return new Privilege(randomAlphabetic(8));
    }
}