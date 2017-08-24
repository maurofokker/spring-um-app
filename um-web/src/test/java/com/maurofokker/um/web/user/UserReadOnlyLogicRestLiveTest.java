package com.maurofokker.um.web.user;

import com.maurofokker.um.client.template.UserRestClient;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<User> {

    @Autowired
    private UserRestClient api;

    public UserReadOnlyLogicRestLiveTest() {
        super(User.class);
    }

    // tests

    // template method

    @Override
    protected final UserRestClient getApi() {return api;}
}
