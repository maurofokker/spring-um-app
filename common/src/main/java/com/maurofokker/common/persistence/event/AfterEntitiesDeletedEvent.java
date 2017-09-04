package com.maurofokker.common.persistence.event;

import com.google.common.base.Preconditions;
import com.maurofokker.common.persistence.model.IEntity;
import org.springframework.context.ApplicationEvent;

/**
 * This event is fired after all entities are deleted.
 */
public final class AfterEntitiesDeletedEvent<T extends IEntity> extends ApplicationEvent {

    private final Class<T> clazz;

    public AfterEntitiesDeletedEvent(final Object sourceToSet, final Class<T> clazzToSet) {
        super(sourceToSet);

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // API

    public final Class<T> getClazz() {
        return clazz;
    }

}
