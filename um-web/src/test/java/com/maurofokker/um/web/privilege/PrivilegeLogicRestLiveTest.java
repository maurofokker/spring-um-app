package com.maurofokker.um.web.privilege;

import com.maurofokker.client.IDtoOperations;
import com.maurofokker.um.client.template.PrivilegeRestClient;
import com.maurofokker.um.model.PrivilegeDtoOpsImpl;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.test.live.UmLogicRestLiveTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrivilegeLogicRestLiveTest extends UmLogicRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient api;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests
    @Test
    public void whenSingleResourceIsRetrievedMultipleTimes_thenThrottled() {
        // Given
        String uriOfExistingResource = getApi().createAsUri(createNewResource());
        ExecutorService executor = Executors.newCachedThreadPool();
        // When (hit 10 times concurrent with executor)
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                getApi().read(uriOfExistingResource);
                System.out.println("Read: " + uriOfExistingResource);
            });
        }
    }

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
