package com.maurofokker.um.web.user;

import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.template.UserRestClient;
import com.maurofokker.um.model.UserDtoOpsImpl;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<User> {

    @Autowired
    private UserRestClient restTemplate;
    @Autowired
    private UserDtoOpsImpl entityOps;

    public UserDiscoverabilityRestLiveTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final User createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final UserRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

}
