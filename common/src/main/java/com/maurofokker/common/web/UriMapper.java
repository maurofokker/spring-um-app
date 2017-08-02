package com.maurofokker.common.web;

import com.maurofokker.common.persistence.model.IEntity;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public class UriMapper implements IUriMapper {

    public UriMapper() {
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    @Override
    public <T extends IEntity> String getUriBase(Class<T> clazz) {
        return clazz.getSimpleName().toString().toLowerCase() + "s";
    }
}
