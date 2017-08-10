package com.maurofokker.test.common.client.template;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.client.marshall.IMarshaller;
import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.common.util.QueryConstants;
import com.maurofokker.common.web.WebConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

public abstract class AbstractSimpleRestClient<T extends IDto> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String JSON = MediaType.APPLICATION_JSON.toString();

    @Autowired
    protected IMarshaller marshaller;

    protected final Class<T> clazz;

    public AbstractSimpleRestClient(final Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        this.clazz = clazz;
    }

    // find - one

    public final T findOne(final long id) {
        final String uriOfResource = getUri() + WebConstants.PATH_SEP + id;
        return findOneByUri(uriOfResource);
    }

    public final Response findOneAsResponse(final long id) {
        final String uriOfResource = getUri() + WebConstants.PATH_SEP + id;
        return findOneByUriAsResponse(uriOfResource);
    }

    public final T findOneByUri(final String uriOfResource) {
        final String resourceAsMime = findOneByUriAsString(uriOfResource);
        return marshaller.decode(resourceAsMime, clazz);
    }

    public final String findOneByUriAsString(final String uriOfResource) {
        final Response response = findOneByUriAsResponse(uriOfResource);
        Preconditions.checkState(response.getStatusCode() == 200);

        return response.asString();
    }

    public final Response findOneByUriAsResponse(final String uriOfResource) {
        return read(uriOfResource);
    }

    // find - all

    public final List<T> findAll() {
        return findAllByUri(getUri());
    }

    public final List<T> findAllByUri(final String uri) {
        final Response allAsResponse = read(uri);
        final List<T> listOfResources = marshaller.<T> decodeList(allAsResponse.getBody().asString(), clazz);
        if (listOfResources == null) {
            return Lists.newArrayList();
        }
        return listOfResources;
    }

    public final Response findAllAsResponse() {
        return findOneByUriAsResponse(getUri());
    }

    // find - all (sorted, paginated)

    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Response findAllResponse = findOneByUriAsResponse(getUri() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder);
        return marshaller.<T> decodeList(findAllResponse.getBody().asString(), clazz);
    }

    public final List<T> findAllPaginated(final int page, final int size) {
        final Response allPaginatedAsResponse = findAllPaginatedAsResponse(page, size);
        return getMarshaller().decodeList(allPaginatedAsResponse.asString(), clazz);
    }

    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Response allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder);
        return getMarshaller().decodeList(allPaginatedAndSortedAsResponse.asString(), clazz);
    }

    public final Response findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findOneByUriAsResponse(uri.toString());
    }

    public final Response findAllSortedAsResponse(final String sortBy, final String sortOrder) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return findOneByUriAsResponse(uri.toString());
    }

    public final Response findAllPaginatedAsResponse(final int page, final int size) {
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        return findOneByUriAsResponse(uri.toString());
    }

    // create

    public final T create(final T resource) {
        final String uriForResourceCreation = createAsUri(resource);
        final String resourceAsMime = findOneByUriAsString(uriForResourceCreation);

        return marshaller.decode(resourceAsMime, clazz);
    }

    public final String createAsUri(final T resource) {
        final Response response = createAsResponse(resource);
        Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

        final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
        Preconditions.checkNotNull(locationOfCreatedResource);
        return locationOfCreatedResource;
    }

    public final Response createAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);
        final RequestSpecification givenAuthenticated = givenAuthenticated();

        return givenAuthenticated.contentType(JSON).body(resource).post(getUri());
    }

    // update

    public final void update(final T resource) {
        final Response updateResponse = updateAsResponse(resource);
        Preconditions.checkState(updateResponse.getStatusCode() == 200, "Update Operation: " + updateResponse.getStatusCode());
    }

    public final Response updateAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);

        return givenAuthenticated().contentType(JSON).body(resource).put(getUri() + "/" + resource.getId());
    }

    // delete

    public final void deleteAll() {
        throw new UnsupportedOperationException();
    }

    public final void delete(final long id) {
        final Response deleteResponse = deleteAsResponse(getUri() + WebConstants.PATH_SEP + id);
        Preconditions.checkState(deleteResponse.getStatusCode() == 204);
    }

    public final Response deleteAsResponse(final String uriOfResource) {
        return givenAuthenticated().delete(uriOfResource);
    }

    // API - other

    public abstract String getUri();

    // count

    public final long count() {
        return Long.valueOf(countAsResponse().asString());
    }

    public final Response countAsResponse() {
        return givenAuthenticated().get(getUri() + "/count");
    }

    // API - other

    public final RequestSpecification givenAuthenticated() {
        final Pair<String, String> credentials = getDefaultCredentials();
        return RestAssured.given().auth().preemptive().basic(credentials.getLeft(), credentials.getRight());
    }

    public final Response read(final String uriOfResource) {
        return readRequest().get(uriOfResource);
    }

    // UTIL

    private final RequestSpecification readRequest() {
        return readRequest(givenAuthenticated());
    }

    private final RequestSpecification readRequest(final RequestSpecification req) {
        return req.header(HttpHeaders.ACCEPT, JSON);
    }

    private final IMarshaller getMarshaller() {
        return marshaller;
    }

    protected abstract Pair<String, String> getDefaultCredentials();

}
