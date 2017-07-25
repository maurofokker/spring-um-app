package com.maurofokker.um;

import com.maurofokker.um.spring.UmContextConfig;
import com.maurofokker.um.spring.UmPersistenceJpaConfig;
import com.maurofokker.um.spring.UmServiceConfig;
import com.maurofokker.um.spring.UmWebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmPersistenceJpaConfig.class, UmContextConfig.class, UmServiceConfig.class, UmWebConfig.class })
@WebAppConfiguration
public class WebSpringIntegrationTest {

    @Test
    public final void whenContextIsBootstrapped_thenOk() {
        //
    }

}
