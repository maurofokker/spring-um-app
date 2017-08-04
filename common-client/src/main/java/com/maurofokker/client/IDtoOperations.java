package com.maurofokker.client;

import com.maurofokker.common.interfaces.IDto;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public interface IDtoOperations<T extends IDto> {

    T createNewResource();

    void invalidate(final T entity);

    void change(final T resource);

}
