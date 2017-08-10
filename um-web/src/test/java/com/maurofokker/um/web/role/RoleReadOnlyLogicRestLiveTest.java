package com.maurofokker.um.web.role;

import com.maurofokker.um.client.template.PrivilegeRestClient;
import com.maurofokker.um.client.template.RoleRestClient;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.test.live.UmReadOnlyLogicRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Role> {

    @Autowired
    private RoleRestClient api;
    @Autowired
    private PrivilegeRestClient associationApi;

    public RoleReadOnlyLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template

    @Override
    protected final RoleRestClient getApi() {
        return api;
    }

    // util

    final PrivilegeRestClient getAssociationAPI() {
        return associationApi;
    }

}
