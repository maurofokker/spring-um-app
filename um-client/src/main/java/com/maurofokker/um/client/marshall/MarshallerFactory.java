package com.maurofokker.um.client.marshall;

import com.maurofokker.client.marshall.IMarshaller;
import com.maurofokker.common.spring.util.Profiles;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Component
@Profile(Profiles.DEPLOYED)
public class MarshallerFactory implements FactoryBean<IMarshaller> {

    public MarshallerFactory() {
        super();
    }

    // API

    @Override
    public IMarshaller getObject() {
        return new ProdJacksonMarshaller();
    }

    @Override
    public Class<IMarshaller> getObjectType() {
        return IMarshaller.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
