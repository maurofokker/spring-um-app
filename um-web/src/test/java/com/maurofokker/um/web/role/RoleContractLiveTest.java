package com.maurofokker.um.web.role;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
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
import java.io.InputStream;
import java.io.InputStreamReader;

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

    // changed the contract to a String, the idea is no rely in the Dto -almost rely, we still use Role-
    private final String createNewResource() throws IOException {
        //final InputStream resourceAsStream = getClass().getResourceAsStream("/data/role_json_01.json"); // load json from resource so is not hardcoded like above comment
        final InputStream resourceAsStream = getClass().getResourceAsStream("/data/role_json_02_new_property.json"); // when breaking contract add second versioned body
        // externalize json into a tree representation this is because we need to modify some values that are uniques or test will fail in second runs
        final JsonNode rootNode = new ObjectMapper().readTree(resourceAsStream); // externalize json into a tree representation this is because we need to modify some values that are uniques or test will fail in second runs
        ((ObjectNode) rootNode).set("name", JsonNodeFactory.instance.textNode(randomAlphabetic(8))); // modifying property so it take a random value to avoid unique constraints
        return rootNode.toString();
    }

    // this is loading a json and transform to string, so is not hardcoded in code
    // also this option could fail because we are repeating values and some constraints may be unique
    private final String createNewResource3() throws IOException {
        final InputStream stream = getClass().getResourceAsStream("/data/role_json_01.json");
        final String roleData = CharStreams.toString(new InputStreamReader(stream)); // using google guava to convert into string
        return roleData;
    }

    // hardcoded body in code
    private final String createNewResource2() {
        final String roleData = "{\"id\":null,\"name\":\"" + randomAlphabetic(8) + "\",\"privileges\":[]}";
        return roleData;
    }

    // contract with dto role
    private final String createNewResource1() {
        final Role newRole = new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
        return getApi().getMarshaller().encode(newRole);
    }

}
