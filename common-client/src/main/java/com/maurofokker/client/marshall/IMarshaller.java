package com.maurofokker.client.marshall;

import java.util.List;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public interface IMarshaller {

    <T> String encode(final T entity);

    <T> T decode(final String entityAsString, final Class<T> clazz);

    <T> List<T> decodeList(final String entitiesAsString, final Class<T> clazz);

    String getMime();

}
