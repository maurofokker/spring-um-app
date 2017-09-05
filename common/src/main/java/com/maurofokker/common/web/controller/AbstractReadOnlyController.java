package com.maurofokker.common.web.controller;

import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.common.persistence.model.IEntity;
import com.maurofokker.common.persistence.service.IRawService;
import com.maurofokker.common.util.QueryConstants;
import com.maurofokker.common.web.RestPreconditions;
import com.maurofokker.common.web.WebConstants;
import com.maurofokker.common.web.events.MultipleResourcesRetrievedEvent;
import com.maurofokker.common.web.events.PaginatedResultsRetrievedEvent;
import com.maurofokker.common.web.events.SingleResourceRetrievedEvent;
import com.maurofokker.common.web.exception.MyResourceNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class AbstractReadOnlyController<D extends IDto, E extends IEntity> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<D> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractReadOnlyController(final Class<D> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // find - one

    protected final D findOneInternal(final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final D resource = findOneInternal(id);
        eventPublisher.publishEvent(new SingleResourceRetrievedEvent<D>(clazz, uriBuilder, response));
        return resource;
    }

    protected final D findOneInternal(final Long id) {
        return (D) RestPreconditions.checkNotNull(getService().findOne(id));
    }

    // find - all

    protected final List<D> findAllInternal(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        if (request.getParameterNames().hasMoreElements()) {
            throw new MyResourceNotFoundException();
        }

        eventPublisher.publishEvent(new MultipleResourcesRetrievedEvent<D>(clazz, uriBuilder, response));
        return (List<D>) getService().findAll();
    }

    protected final void findAllRedirectToPagination(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String locationValue = uriBuilder.path(WebConstants.PATH_SEP + resourceName).build().encode().toUriString() + QueryConstants.QUESTIONMARK + "page=0&size=10";

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    protected final List<D> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<D>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findPaginatedInternal(final int page, final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginatedRaw(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<D>(clazz, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<D> resultPage = (List<D>) getService().findAllSorted(sortBy, sortOrder);
        return resultPage;
    }

    // count

    protected final long countInternal() {
        // InvalidDataAccessApiUsageException dataEx - ResourceNotFoundException
        return getService().count();
    }

    // generic REST operations

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

}

