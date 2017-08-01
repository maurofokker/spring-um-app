package com.maurofokker.common.client;

import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public class CustomCollectionConverter extends CollectionConverter {

    public CustomCollectionConverter(final Mapper mapper) {
        super(mapper);
    }
}
