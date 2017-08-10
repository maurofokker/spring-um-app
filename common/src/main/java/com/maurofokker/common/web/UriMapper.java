package com.maurofokker.common.web;

import com.maurofokker.common.persistence.model.IEntity;
import org.springframework.stereotype.Component;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
@Component
public class UriMapper implements IUriMapper {

    public UriMapper() {
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    @Override
    public <T extends IEntity> String getUriBase(Class<T> clazz) {
        String simpleName = clazz.getSimpleName().toString().toLowerCase();
        if (simpleName.endsWith("dto")) {
            simpleName = simpleName.substring(0, simpleName.length() - 3);
        }
        return simpleName + "s";
    }
}
