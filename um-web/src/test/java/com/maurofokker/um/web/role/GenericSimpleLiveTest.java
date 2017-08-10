package com.maurofokker.um.web.role;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.common.interfaces.INameableDto;
import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.common.util.SearchField;
import com.maurofokker.common.web.WebConstants;
import com.maurofokker.test.common.util.IDUtil;
import com.maurofokker.um.client.template.GenericSimpleApiClient;
import com.maurofokker.um.client.template.RoleSimpleApiClient;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.spring.CommonTestConfig;
import com.maurofokker.um.spring.UmClientConfig;
import com.maurofokker.um.spring.UmLiveTestConfig;
import org.apache.http.HttpHeaders;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collection;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@ActiveProfiles({Profiles.CLIENT, Profiles.TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class GenericSimpleLiveTest<T extends INameableDto> {

    private final static String JSON = MediaType.APPLICATION_JSON.toString();

    @Autowired
    private GenericSimpleApiClient<T> api;

    // find - one

    // find - all

    @Test
    public final void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().read(getUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination

    @Test
    public final void whenResourcesAreRetrievedPaginated_then200IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(0, 1);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenPageOfResourcesIsRetrievedOutOfBounds_then404IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAsResponse(Integer.parseInt(randomNumeric(5)), 1);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPage_then400IsReceived() {
        // When
        final Response response = getApi().findByUriAsResponse(getUri() + "?page=" + randomAlphabetic(5).toLowerCase() + "&size=1");

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourcesAreRetrievedWithNonNumericPageSize_then400IsReceived() {
        // When
        final Response response = getApi().findByUriAsResponse(getUri() + "?page=0" + "&size=" + randomAlphabetic(5));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // find - all - sorting

    @Test
    public final void whenResourcesAreRetrievedSorted_then200IsReceived() {
        final Response response = getApi().findAllSortedAsResponse(SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    // find - all - pagination and sorting

    @Test
    public final void whenResourcesAreRetrievedPaginatedAndSorted_then200IsReceived() {
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 1, SearchField.name.name(), Sort.Direction.ASC.name());

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourcesAreRetrievedByPaginatedAndWithInvalidSorting_then400IsReceived() {
        // When
        final Response response = getApi().findAllPaginatedAndSortedAsResponse(0, 4, "invalid", null);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    // count

    @Test
    public final void whenCountIsPerformed_then200IsReceived() {
        // When
        final Response response = getApi().countAsResponse();

        // Then
        assertThat(response.getStatusCode(), is(200));
    }
    // create

    @Test
    public void whenResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    @Test
    public final void givenResourceHasNameWithSpace_whenResourceIsCreated_then201IsReceived() {
        final T newResource = createNewResource();
        newResource.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // When
        final Response createAsResponse = getApi().createAsResponse(newResource);

        // Then
        assertThat(createAsResponse.getStatusCode(), is(201));
    }

    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final T newResource = createNewResource();
        getAssociations(newResource).add(createNewAssociationResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Privilege invalidAssociation = createNewAssociationResource();
        invalidAssociation.setName(null);
        final T newResource = createNewResource();
        getAssociations(newResource).add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceWithUnsupportedMediaTypeIsCreated_then415IsReceived() {
        // When
        final Response response = getApi().givenAuthenticated().contentType("unknown").post(getUri());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }

    @Test
    public final void whenResourceIsCreatedWithNonNullId_then409IsReceived() {
        final T resourceWithId = createNewResource();
        resourceWithId.setId(5l);

        // When
        final Response response = getApi().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenResourceIsCreated_thenResponseContainsTheLocationHeader() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public final void givenResourceExsits_whenResourceWithSameAttributeIsCreated_then409IsReceived() {
        // Given
        final T newEntity = createNewResource();
        getApi().createAsResponse(newEntity);

        // when
        final Response response = getApi().createAsResponse(newEntity);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    // update

    @Test
    public final void givenResourceExists_whenResourceIsUpdated_then200IsReceived() {
        // Given
        final T existingResource = getApi().create(createNewResource());

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void givenInvalidResource_whenResourceIsUpdated_then400BadRequestIsReceived() {
        // Given
        final T existingResource = getApi().create(createNewResource());
        existingResource.setName(null);

        // When
        final Response response = getApi().updateAsResponse(existingResource);

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenResourceIsUpdatedWithNullId_then400IsReceived() {
        // When
        final Response response = getApi().updateAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void whenNullResourceIsUpdated_then400IsReceived() {
        // When
        final Response response = getApi().givenAuthenticated().contentType(JSON).put(getUri() + "/" + randomAlphanumeric(4));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceDoesNotExist_whenResourceIsUpdated_then404IsReceived() {
        // Given
        final T unpersistedResource = createNewResource();
        unpersistedResource.setId(IDUtil.randomPositiveLong());

        // When
        final Response response = getApi().updateAsResponse(unpersistedResource);

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    // delete

    @Test
    public final void givenResourceExists_whenResourceIsDeleted_then204IsReceived() {
        // Given
        final long idOfResource = getApi().create(createNewResource()).getId();

        // When
        final Response response = getApi().deleteAsResponse(idOfResource);

        // Then
        assertThat(response.getStatusCode(), is(204));
    }

    @Test
    public final void whenResourceIsDeletedByIncorrectNonNumericId_then400IsReceived() {
        // When
        final Response response = getApi().givenAuthenticated().delete(getUri() + randomAlphabetic(6));

        // Then
        assertThat(response.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceDoesNotExist_whenResourceIsDeleted_then404IsReceived() {
        // When
        final Response response = getApi().deleteAsResponse(Long.parseLong(randomNumeric(6)));

        // Then
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void givenResourceExistedAndWasDeleted_whenRetrievingResource_then404IsReceived() {
        // Given
        final long idOfResource = getApi().create(createNewResource()).getId();
        getApi().deleteAsResponse(idOfResource);

        // When
        final Response getResponse = getApi().findOneAsResponse(idOfResource);

        // Then
        assertThat(getResponse.getStatusCode(), is(404));
    }

    // mime

    @Test
    public final void givenRequestAcceptsMime_whenResourceIsRetrievedById_thenResponseContentTypeIsMime() {
        // Given
        final long idOfCreatedResource = getApi().create(createNewResource()).getId();

        // When
        final Response res = getApi().findOneAsResponse(idOfCreatedResource);

        // Then
        assertThat(res.getContentType(), StringContains.containsString(MediaType.APPLICATION_JSON.toString()));
    }

    // UTIL

    private final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    protected abstract GenericSimpleApiClient<T> getApi();

    protected abstract T createNewResource();

    protected abstract <A extends IDto> Collection<A> getAssociations(T resource);

    protected abstract <A extends IDto> A createNewAssociationResource();
}