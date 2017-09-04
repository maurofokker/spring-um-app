package com.maurofokker.common.web.controller;

import com.maurofokker.common.persistence.model.IEntity;
import com.maurofokker.common.persistence.service.IRawService;
import com.maurofokker.common.web.RestPreconditions;
import com.maurofokker.common.web.exception.MyResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHateoasController<D extends ResourceSupport, E extends IEntity> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    // find - one

    protected final D findOneInternal(final Long id) {
        final E entityNotNull = RestPreconditions.checkNotNull(getService().findOne(id));
        return convert(entityNotNull);
    }

    // find - all

    protected final List<D> findAllInternal(final HttpServletRequest request) {
        if (request.getParameterNames().hasMoreElements()) {
            throw new MyResourceNotFoundException();
        }

        return convertList(getService().findAll());
    }

    protected final List<D> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<E> resultPage = getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }

        return convertList(resultPage.getContent());
    }

    protected final List<D> findPaginatedInternal(final int page, final int size) {
        final Page<E> resultPage = getService().findAllPaginatedRaw(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }

        return convertList(resultPage.getContent());
    }

    protected final List<D> findAllSortedInternal(final String sortBy, final String sortOrder) {
        return convertList(getService().findAllSorted(sortBy, sortOrder));
    }

    // count

    protected final long countInternal() {
        // InvalidDataAccessApiUsageException dataEx - ResourceNotFoundException
        return getService().count();
    }

    // save/create/persist

    protected final void createInternal(final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        getService().create(resource);
    }

    // update

    /**
     * - note: the operation is IDEMPOTENT <br/>
     */
    protected final void updateInternal(final long id, final E resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
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

    // count

    /**
     * Counts all {@link Privilege} resources in the system
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public long count() {
        return countInternal();
    }

    // template method

    protected abstract IRawService<E> getService();

    private final List<D> convertList(final List<E> entities) {
        return entities.stream().map(this::convert).collect(Collectors.toList());
    }

    protected abstract D convert(final E entity);

}
