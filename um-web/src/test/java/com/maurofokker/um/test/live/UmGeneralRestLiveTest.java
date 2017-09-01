package com.maurofokker.um.test.live;

import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UmGeneralRestLiveTest {
}
