package com.maurofokker.um.test.suite;

import com.maurofokker.um.common.search.ConstructQueryStringUnitTest;
import com.maurofokker.um.service.impl.PrivilegeServiceUnitTest;
import com.maurofokker.um.service.impl.RoleServiceUnitTest;
import com.maurofokker.um.service.impl.UserServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserServiceUnitTest.class, RoleServiceUnitTest.class, PrivilegeServiceUnitTest.class, ConstructQueryStringUnitTest.class })
public final class UnitSuite {
    //
}
