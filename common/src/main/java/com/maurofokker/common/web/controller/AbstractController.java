package com.maurofokker.common.web.controller;

import com.maurofokker.common.persistence.model.IEntity;
import com.maurofokker.common.web.RestPreconditions;
import com.maurofokker.common.web.events.AfterResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController<T extends IEntity> extends AbstractReadOnlyController<T> {

    @Autowired
    public AbstractController(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // save/create/persist

    protected final void createInternal(final T resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        final T existingResource = getService().create(resource);
        // - note: mind the autoboxing and potential NPE when the resource has null id at this point (likely when working with DTOs)
        eventPublisher.publishEvent(new AfterResourceCreatedEvent<T>(clazz, uriBuilder, response, existingResource.getId().toString()));
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final long id, final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkIfBadRequest(resource.getId() == id, resource.getClass().getSimpleName() + "id and the URI id don't match");
        RestPreconditions.checkRequestState(resource.getId() == id);
        RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

        getService().update(resource);
    }

    // delete/remove

    protected final void deleteByIdInternal(final long id) {
        // InvalidDataAccessApiUsageException - ResourceNotFoundException
        // IllegalStateException - ResourceNotFoundException
        // DataAccessException - ignored
        getService().delete(id);
    }

}
