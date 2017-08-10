package com.maurofokker.um.web.privilege;

import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.template.PrivilegeRestClient;
import com.maurofokker.um.model.PrivilegeDtoOpsImpl;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.test.live.UmLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PrivilegeLogicRestLiveTest extends UmLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient api;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    // template

    //@Override
    protected final PrivilegeRestClient getApi() {
        return api;
    }

    //@Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}
