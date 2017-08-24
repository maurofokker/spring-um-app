package com.maurofokker.test.common.client.security;

import com.google.common.base.Preconditions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
public class ClientAuthenticationComponent implements ITestBasicAuthenticator {

    public ClientAuthenticationComponent() {
        super();
    }

    // API

    @Override
    public final RequestSpecification givenBasicAuthenticated(final String username, final String password) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        return RestAssured.given().auth().preemptive().basic(username, password);
    }

}
