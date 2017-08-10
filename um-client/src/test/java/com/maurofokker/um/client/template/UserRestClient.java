package com.maurofokker.um.client.template;

import com.maurofokker.test.common.client.template.AbstractRestClient;
import com.maurofokker.um.client.UmPaths;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.util.Um;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * Created by mgaldamesc on 10-08-2017.
 */
@Component
@Profile("client")
public final class UserRestClient extends AbstractRestClient<User> {

    @Autowired
    protected UmPaths paths;

    public UserRestClient() {
        super(User.class);
    }

    // API

    // template method

    @Override
    public final String getUri() {
        return paths.getUserUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}