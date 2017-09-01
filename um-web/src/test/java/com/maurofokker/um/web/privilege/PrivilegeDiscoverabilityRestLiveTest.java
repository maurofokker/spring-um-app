package com.maurofokker.um.web.privilege;

import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.template.PrivilegeRestClient;
import com.maurofokker.um.model.PrivilegeDtoOpsImpl;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.test.live.UmDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient restTemplate;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeDiscoverabilityRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template method

    @Override
    protected final Privilege createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final PrivilegeRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
