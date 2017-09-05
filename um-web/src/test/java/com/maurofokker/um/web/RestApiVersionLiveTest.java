package com.maurofokker.um.web;

import com.maurofokker.um.run.UmApp;
import com.maurofokker.um.spring.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UmApp.class, UmWebConfig.class, UmServletConfig.class, UmServiceConfig.class, UmPersistenceJpaConfig.class, UmContextConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiVersionLiveTest {

    private static final String RESOURCE_URI = "/countv";
    private static final String APP_ROOT = "/um-webapp/api/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetResourceWithoutVersion_thenNotFound() {
        final ResponseEntity<String> response = restTemplate.getForEntity(APP_ROOT + RESOURCE_URI, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenGetResourceWithParamVersion_thenOk() {
        final ResponseEntity<String> response = restTemplate.getForEntity(APP_ROOT + RESOURCE_URI + "?v=1.0", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetResourceWithHeaderVersion_thenOk() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.resource.v1.0+json");

        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<String> response = restTemplate.exchange(APP_ROOT + RESOURCE_URI, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenGetResourceWithUrlVersion_thenOk() {
        final ResponseEntity<String> response = restTemplate.getForEntity(APP_ROOT + "/v1.0" + RESOURCE_URI, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
