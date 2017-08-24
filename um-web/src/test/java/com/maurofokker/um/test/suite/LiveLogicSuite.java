package com.maurofokker.um.test.suite;

import com.maurofokker.um.security.AuthenticationRestLiveTest;
import com.maurofokker.um.web.privilege.PrivilegeLogicRestLiveTest;
import com.maurofokker.um.web.privilege.PrivilegeReadOnlyLogicRestLiveTest;
import com.maurofokker.um.web.role.RoleLogicRestLiveTest;
import com.maurofokker.um.web.role.RoleReadOnlyLogicRestLiveTest;
import com.maurofokker.um.web.user.UserLogicRestLiveTest;
import com.maurofokker.um.web.user.UserReadOnlyLogicRestLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by mgaldamesc on 10-08-2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
        UserLogicRestLiveTest.class,
        UserReadOnlyLogicRestLiveTest.class,

        PrivilegeLogicRestLiveTest.class,
        PrivilegeReadOnlyLogicRestLiveTest.class,

        RoleLogicRestLiveTest.class,
        RoleReadOnlyLogicRestLiveTest.class,

        AuthenticationRestLiveTest.class
})
// @formatter:off
public final class LiveLogicSuite {
}
