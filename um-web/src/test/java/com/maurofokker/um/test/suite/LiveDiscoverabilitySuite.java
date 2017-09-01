package com.maurofokker.um.test.suite;

import com.maurofokker.um.common.root.RootDiscoverabilityRestLiveTest;
import com.maurofokker.um.web.privilege.PrivilegeDiscoverabilityRestLiveTest;
import com.maurofokker.um.web.role.RoleDiscoverabilityRestLiveTest;
import com.maurofokker.um.web.user.UserDiscoverabilityRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserDiscoverabilityRestLiveTest.class, RoleDiscoverabilityRestLiveTest.class, PrivilegeDiscoverabilityRestLiveTest.class, RootDiscoverabilityRestLiveTest.class })
public final class LiveDiscoverabilitySuite {
    //
}
