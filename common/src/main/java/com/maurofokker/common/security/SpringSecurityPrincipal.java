package com.maurofokker.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public final class SpringSecurityPrincipal extends User {
    private final String uuid;

    public SpringSecurityPrincipal(final String username, final String password, final boolean enabled, final Collection<? extends GrantedAuthority> authorities, final String uuidToSet) {
        super(username, password, enabled, true, true, true, authorities);

        uuid = uuidToSet;
    }

    // API

    public final String getUuid() {
        return uuid;
    }
}
