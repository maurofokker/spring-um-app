package com.maurofokker.um.test.live;

import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.test.common.web.AbstractDiscoverabilityLiveTest;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UmDiscoverabilityRestLiveTest<T extends IDto> extends AbstractDiscoverabilityLiveTest<T> {

    public UmDiscoverabilityRestLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

}
