package com.maurofokker.common.persistence.event;

import com.google.common.base.Preconditions;
import com.maurofokker.common.persistence.model.IEntity;
import org.springframework.context.ApplicationEvent;

/**
 * This event should be fired when entity is updated.
 */
public final class AfterEntityUpdateEvent<T extends IEntity> extends ApplicationEvent {

    private final Class<T> clazz;
    private final T entity;

    public AfterEntityUpdateEvent(final Object sourceToSet, final Class<T> clazzToSet, final T entityToSet) {
        super(sourceToSet);

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;

        Preconditions.checkNotNull(entityToSet);
        entity = entityToSet;
    }

    // API

    public final Class<T> getClazz() {
        return clazz;
    }

    public final T getEntity() {
        return Preconditions.checkNotNull(entity);
    }

}
