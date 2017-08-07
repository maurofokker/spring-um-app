package com.maurofokker.um.web.role;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import com.maurofokker.um.util.Um;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@ActiveProfiles({Profiles.CLIENT, Profiles.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleBasicLiveTest {

    private final static String URI = "http://localhost:8086/api/roles";

    // tests

    @Test
    public void whenAllRolesAreRetrieved_then200OK() {
        final RequestSpecification basicAuth = RestAssured.given().auth().preemptive().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
        // preemptive() is used bc is not from a browser, and we dont want to get prompted for credentials and then gives basic() auth info
        final Response response = basicAuth.accept(ContentType.JSON).get(URI);

        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
    }

}
