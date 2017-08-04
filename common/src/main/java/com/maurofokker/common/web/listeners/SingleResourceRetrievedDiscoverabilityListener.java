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
     */
    @SuppressWarnings("unchecked")
    final void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(WebConstants.PATH_SEP + uriMapper.getUriBase(clazz)).build().encode().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}