package com.maurofokker.um.web.role;


import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.template.RoleRestClient;
import com.maurofokker.um.model.RoleDtoOpsImpl;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Role> {

    @Autowired
    private RoleRestClient restTemplate;
    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleDiscoverabilityRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final RoleRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
