package com.maurofokker.um.model;

import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.FixtureResourceFactory;
import com.maurofokker.um.persistence.model.User;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Component
public final class UserDtoOpsImpl implements IDtoOperations<User> {

    public UserDtoOpsImpl() {
        super();
    }

    // API

    // template method

    @Override
    public final User createNewResource() {
        return FixtureResourceFactory.createNewUser();
    }

    @Override
    public final void invalidate(final User entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final User resource) {
        resource.setName(randomAlphabetic(8));
    }

}
