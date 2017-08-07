package com.maurofokker.um.test.suite;

import com.maurofokker.um.service.main.PrivilegeServiceIntegrationTest;
import com.maurofokker.um.service.main.RoleServiceIntegrationTest;
import com.maurofokker.um.service.main.UserServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
    UserServiceIntegrationTest.class,
    PrivilegeServiceIntegrationTest.class,
    RoleServiceIntegrationTest.class
})
// @formatter:on
public final class ServiceSuite {
    //
}
