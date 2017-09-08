package com.maurofokker.um.web.user;

import com.google.common.collect.Sets;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.maurofokker.client.IDtoOperations;
import com.maurofokker.client.marshall.IMarshaller;
import com.maurofokker.test.common.client.security.ITestAuthenticator;
import com.maurofokker.um.client.FixtureResourceFactory;
import com.maurofokker.um.client.template.RoleRestClient;
import com.maurofokker.um.client.template.UserRestClient;
import com.maurofokker.um.model.RoleDtoOpsImpl;
import com.maurofokker.um.model.UserDtoOpsImpl;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.service.AsyncService;
import com.maurofokker.um.test.live.UmLogicRestLiveTest;
import com.maurofokker.um.util.Um;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class UserLogicRestLiveTest extends UmLogicRestLiveTest<User> {

    @Autowired
    private UserRestClient api;

    @Autowired
    private RoleRestClient associationApi;

    @Autowired
    private UserDtoOpsImpl entityOps;
    @Autowired
    private RoleDtoOpsImpl associationOps;

    @Autowired
    protected ITestAuthenticator auth;

    @Autowired
    protected IMarshaller marshaller;

    public UserLogicRestLiveTest() {
        super(User.class);
    }

    // Tests

    // find - one

    @Test
    @Ignore("in progress - create association first")
    public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved() {
        final User existingResource = getApi().create(getEntityOps().createNewResource());
        assertThat(existingResource.getRoles(), not(Matchers.<Role> empty()));
    }

    // create

    /**
     * - note: this test ensures that a new User cannot automatically create new Privileges <br>
     * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
     */
    @Test
    public final void whenResourceIsCreatedWithNewAssociation_then409IsReceived() {
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(getAssociationEntityOps().createNewResource());

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    @Ignore("intermitent failures - temporarily ignored")
    public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived() {
        final Role invalidAssociation = getAssociationEntityOps().createNewResource();
        invalidAssociation.setId(1001l);
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(invalidAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    @Test
    public final void whenUserIsCreatedWithExistingRole_then201IsReceived() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        // When
        final Response response = getApi().createAsResponse(newResource);

        // Then
        assertThat(response.getStatusCode(), is(201));
    }

    /**
     * long running creation user
     */
    @Ignore // need to fix
    @Test
    public void whenCreateUserAsyncUsingCallable_thenCreatedWithDelay() {
        String api = getApi().getUri() + "/callable";
        System.out.println("---> " + api);

        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        Response response = createRandomUser(newResource).post(api);

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
        assertTrue(response.time() > AsyncService.DELAY);
    }

    @Ignore // need to fix
    @Test
    public void whenCreateUserAsyncUsingDeferredResult_thenCreatedWithDelay() {
        String api = getApi().getUri() + "/deferred";
        System.out.println("---> " + api);

        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        Response response = createRandomUser(newResource).post(api);

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
        assertTrue(response.time() > AsyncService.DELAY);
    }

    @Ignore // need to fix
    @Test
    public void whenCreateUserAsync_thenAccepted() throws InterruptedException {

        String api = getApi().getUri() + "/async";
        System.out.println("---> " + api);

        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User newResource = getEntityOps().createNewResource();
        newResource.getRoles().add(existingAssociation);

        Response response = createRandomUser(newResource).post(api);
        //Response response = createRandomUser().post(getApi().getUri() + "/async");

        assertEquals(202, response.getStatusCode());
        assertTrue(response.time() < AsyncService.DELAY);
        String loc = response.getHeader("Location");
        assertNotNull(loc);

        // Get to know if resource is created (status)
        Response checkLocResponse = getApi().givenReadAuthenticated().get(loc);
        assertTrue(checkLocResponse.getStatusCode() == 200);
        assertTrue(checkLocResponse.asString().contains("In Progress"));

        Thread.sleep(AsyncService.DELAY);
        Response finalLocResponse = getApi().givenReadAuthenticated().get(loc);
        assertEquals(200, finalLocResponse.getStatusCode());
        assertTrue(finalLocResponse.asString().contains("Ready"));
    }

    // TODO: sort

    @Test
    public final void whenScenario_getResource_getAssociationsById() {
        final Role existingAssociation = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User resourceToCreate = getEntityOps().createNewResource();
        resourceToCreate.getRoles().add(existingAssociation);

        // When
        final User existingResource = getApi().create(resourceToCreate);
        for (final Role associationOfResourcePotential : existingResource.getRoles()) {
            final Role existingAssociationOfResource = getAssociationAPI().findOne(associationOfResourcePotential.getId());
            assertThat(existingAssociationOfResource, notNullValue());
        }
    }

    // scenarios

    @Test
    public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted() {
        final Role child = getAssociationAPI().create(getAssociationEntityOps().createNewResource());
        final User parent = FixtureResourceFactory.createNewUser();
        parent.setRoles(Sets.newHashSet(child));
        final User parentWithChild = getApi().create(parent);
        assertThat(parentWithChild.getRoles(), hasItem(child));

        final User parent2 = FixtureResourceFactory.createNewUser();
        parent2.setRoles(Sets.newHashSet(child));
        getApi().createAsResponse(parent2);

        final User resource1ViewOfServerAfter = getApi().findOne(parentWithChild.getId());
        assertThat(resource1ViewOfServerAfter.getRoles(), hasItem(child));
    }

    //

    private RequestSpecification createRandomUser(User newResource) {
        //return RestAssured.given().auth().basic(Um.ADMIN_EMAIL, Um.ADMIN_PASS).contentType(MediaType.APPLICATION_JSON_VALUE).body(getEntityOps().createNewResource());
        String resourceAsString = marshaller.encode(newResource);
        return givenReadAuthenticated().contentType(MediaType.APPLICATION_JSON_VALUE).body(resourceAsString);
    }


    // template methods

    @Override
    protected final UserRestClient getApi() { return api;}

    @Override
    protected IDtoOperations<User> getEntityOps() {
        return entityOps;
    }

    final RoleRestClient getAssociationAPI() { return associationApi; }

    final IDtoOperations<Role> getAssociationEntityOps() { return associationOps; }
}
