package com.maurofokker.common.web.listeners;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.maurofokker.common.util.LinkUtil;
import com.maurofokker.common.web.IUriMapper;
import com.maurofokker.common.web.WebConstants;
import com.maurofokker.common.web.events.SingleResourceRetrievedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mgaldamesc on 03-08-2017.
 * Add metadata information to the response to provide more information back to the client
 * Listen for a event triggered in the controller when a resource is retrieved
 * - when a GET of a single resource is call, this event is being fired and this listener listen that event
 */
@SuppressWarnings("rawtypes")
@Component
class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public SingleResourceRetrievedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final SingleResourceRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverGetAllURI(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     * - when single resource is retrieved, this will provide the URL of the collection resource
     *  i.e. if we retrieve /privilege/1 then this provide the URL /privileges
     */
    @SuppressWarnings("unchecked")
    final void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        // get the collection URI this can be used for collection or single resource
        final String uriForResourceCreation = uriBuilder.path(WebConstants.PATH_SEP + uriMapper.getUriBase(clazz)).build().encode().toUriString();

        // after the URI is added as a Link Header, because this allows to embed a lot of types of links in the header
        // this can be done with LinkUtil.REL_COLLECTION and this looks like this in client side header
        // Link     : <http://localhost:8086/um-web/api/privileges>; rel="collection"
        // rel types are standardized
        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}