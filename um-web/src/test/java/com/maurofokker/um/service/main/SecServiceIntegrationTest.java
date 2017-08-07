package com.maurofokker.um.service.main;

import com.maurofokker.common.persistence.model.INameableEntity;
import com.maurofokker.test.common.service.AbstractServiceIntegrationTest;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmContextConfig;
import com.maurofokker.um.spring.UmPersistenceJpaConfig;
import com.maurofokker.um.spring.UmServiceConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmPersistenceJpaConfig.class, UmServiceConfig.class, UmContextConfig.class, UmClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecServiceIntegrationTest<T extends INameableEntity> extends AbstractServiceIntegrationTest<T> {

    //

}
