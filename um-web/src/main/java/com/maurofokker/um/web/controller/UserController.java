package com.maurofokker.um.web.controller;

import com.maurofokker.common.security.SpringSecurityUtil;
import com.maurofokker.common.util.QueryConstants;
import com.maurofokker.common.web.controller.AbstractController;
import com.maurofokker.common.web.controller.ISortingController;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.security.UmUser;
import com.maurofokker.um.service.AsyncService;
import com.maurofokker.um.service.IUserService;
import com.maurofokker.um.util.Um;
import com.maurofokker.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@RequestMapping(value = UmMappings.USERS)
public class UserController extends AbstractController<User> implements ISortingController<User> {

    @Autowired
    private IUserService service;

    @Autowired
    private AsyncService asyncService;

    public UserController() {
        super(User.class);
    }

    // API

    // find - all/paginated

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public List<User> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                   @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder,
                                                   final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public List<User> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size,
                                          final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedInternal(page, size, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public List<User> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public List<User> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // find - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public User findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_USER_READ)
    public User current() {
        final UmUser currentUser = (UmUser) SpringSecurityUtil.getCurrentUserDetails();
        if (currentUser == null) {
            return null;
        }
        return findOneInternal(currentUser.getId());
    }

    @RequestMapping("/user")
    public UmUser user(final UmUser user) {
        return user;
    }

    // create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    /**
     * This will keep client thread blocked but container thread not
     * Processing is handle by it self and managed by spring mvc
     *
     */
    @RequestMapping(value = "/callable", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Callable<User> createUserWithCallable(@RequestBody @Valid final User resource) {
        return new Callable<User>() {

            @Override
            public User call() throws Exception {
                return service.createSlow(resource);
            }
        };
    }

    /**
     * Same as Callable in terms that client thread is still blocked and servlet container thread not
     * But it gives more control in setting the value of the deferred result
     * Full control over when and how processing will happen
     * Gives more flexibility as to when we can call the set result on our DeferredResult and how we can
     * handle all of that processing
     */
    @RequestMapping(value = "/deferred", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<User> createUserWithDeferredResult(@RequestBody final User resource) {
        final DeferredResult<User> result = new DeferredResult<User>();
        asyncService.scheduleCreateUser(resource, result);
        return result;
    }

    /**
     * trigger async operation in the service layer, and this will be executed in different thread
     * it return to client SC 202 Accepted along with Location header, this is the necessary information for client to send a GET req and verify status of this operation
     */
    @RequestMapping(value = "/async", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUserWithAsync(@RequestBody final User resource, HttpServletResponse response, UriComponentsBuilder uriBuilder) throws InterruptedException {
        asyncService.createUserAsync(resource);
        final String location = uriBuilder.path("/users").queryParam("name", resource.getName()).build().encode().toString();
        response.setHeader("Location", location);
    }

    // update

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Um.Privileges.CAN_USER_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody @Valid final User resource) {
        updateInternal(id, resource);
    }

    // delete

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Um.Privileges.CAN_USER_WRITE)                          // method level security
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IUserService getService() {
        return service;
    }
}
