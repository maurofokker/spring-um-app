package com.maurofokker.common.security;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class Base64UnitTest {

    private static final String BASIC_AUTHORIZATION = "Basic bWdhbGRhbWVzQGdtYWlsLmNvbTptZ2FsZGFtZXM";

    // tests

    @Test
    public final void whenAuthorizationHeaderIsDecoded_thenNoExceptions() {
        SpringSecurityUtil.decodeAuthorizationKey(BASIC_AUTHORIZATION);
    }

    @Test
    public final void whenAuthorizationHeaderIsDecoded_thenResponseNotNull() {
        final Pair<String, String> authorizationKey = SpringSecurityUtil.decodeAuthorizationKey(BASIC_AUTHORIZATION);

        assertNotNull(authorizationKey);
        assertNotNull(authorizationKey.getLeft());
        assertNotNull(authorizationKey.getRight());
    }

    @Test
    public final void whenAuthorizationHeaderIsDecoded_thenResponseIsCorrect() {
        final Pair<String, String> authorizationKey = SpringSecurityUtil.decodeAuthorizationKey(BASIC_AUTHORIZATION);

        assertThat(authorizationKey.getLeft(), equalTo("mgaldames@gmail.com"));
        assertThat(authorizationKey.getRight(), equalTo("mgaldames"));
    }

}
