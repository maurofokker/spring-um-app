package com.maurofokker.um.web.event;

import com.maurofokker.common.web.listeners.ResourceCreatedDiscoverabilityListener;
import com.maurofokker.um.util.UmMappings;
import org.springframework.stereotype.Component;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Component
public class SecResourceCreatedDiscoverabilityListener extends ResourceCreatedDiscoverabilityListener {

    public SecResourceCreatedDiscoverabilityListener() {
        super();
    }

    @Override
    protected String getBase() {
        return UmMappings.BASE;
    }
}
