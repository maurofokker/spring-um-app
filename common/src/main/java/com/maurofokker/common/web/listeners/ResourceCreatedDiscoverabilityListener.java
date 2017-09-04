package com.maurofokker.common.web.listeners;

import com.google.common.base.Preconditions;
import com.maurofokker.common.web.IUriMapper;
import com.maurofokker.common.web.events.AfterResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
/*
Discoverability listener or HATEOAS listener meant for resource creation
 */
public abstract class ResourceCreatedDiscoverabilityListener implements ApplicationListener<AfterResourceCreatedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public ResourceCreatedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final AfterResourceCreatedEvent ev) {
        Preconditions.checkNotNull(ev);

        /*
        extract information from the event and then add the new location header in method
        addLinkHeaderOnEntityCreation
         */
        final String idOfNewResource = ev.getIdOfNewResource();
        addLinkHeaderOnEntityCreation(ev.getUriBuilder(), ev.getResponse(), idOfNewResource, ev.getClazz());
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    protected void addLinkHeaderOnEntityCreation(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final String idOfNewEntity, final Class clazz) {
        final String path = calculatePathToResource(clazz);
        final String locationValue = uriBuilder.path(path).build().expand(idOfNewEntity).encode().toUriString();

        /*
        INFO
        we have full flexibility of providing any kind of mapping here. In a complex API we could do a one-to-one mapping,
        and more complex heuristics, anything we want as long as we can calculate that mapping, and we can because we are
        controlling the API and we know what our URLs are so we can DO the next setHeader
         */
        response.setHeader(org.springframework.http.HttpHeaders.LOCATION, locationValue);
        /*
        INFO for a client side is useful to have this kind of information
         */
    }

    /**
     * Calculate path of resource based on some conventions
     * - assuming that the right URL is going to have the name of the resource class
     * @param clazz
     * @return
     */
    protected String calculatePathToResource(final Class clazz) {
        final String resourceName = uriMapper.getUriBase(clazz);
        final String path = getBase() + resourceName + "/{id}";
        return path;
    }

    protected abstract String getBase();

}