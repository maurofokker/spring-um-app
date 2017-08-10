package com.maurofokker.um.test.suite;

import com.maurofokker.um.security.SecurityRestLiveSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by mgaldamesc on 10-08-2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({  // @formatter:off
        LiveLogicSuite.class,
        SecurityRestLiveSuite.class
}) // @formatter:on
public final class LiveSuite {
}
