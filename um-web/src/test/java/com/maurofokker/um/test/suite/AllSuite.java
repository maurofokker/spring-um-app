package com.maurofokker.um.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LiveSuite.class, UnitSuite.class, ServiceSuite.class })
public final class AllSuite {
    //
}
