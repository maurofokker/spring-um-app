package com.maurofokker.test.common.client.security;

import com.jayway.restassured.specification.RequestSpecification;

public interface ITestBasicAuthenticator {

    RequestSpecification givenBasicAuthenticated(final String username, final String password);
}
