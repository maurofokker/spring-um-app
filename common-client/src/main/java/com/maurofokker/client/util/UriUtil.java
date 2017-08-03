package com.maurofokker.client.util;

import org.springframework.web.util.UriTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public final class UriUtil {

    private UriUtil() {
        throw new AssertionError();
    }

    // API

    public static URI createSearchUri(final String uriBase, final String paramToExpand) {
        URL url = null;
        try {
            url = new UriTemplate(uriBase).expand(paramToExpand).toURL();
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        }

        try {
            return url.toURI();
        } catch (final URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
