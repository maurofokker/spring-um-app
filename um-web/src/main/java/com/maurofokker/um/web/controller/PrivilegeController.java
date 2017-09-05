package com.maurofokker.um.web.controller;

import com.maurofokker.common.util.QueryConstants;
import com.maurofokker.common.web.controller.AbstractController;
import com.maurofokker.common.web.controller.ISortingController;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.service.IPrivilegeService;
import com.maurofokker.um.util.Um;
import com.maurofokker.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = UmMappings.Plural.PRIVILEGES)
public class PrivilegeController extends AbstractController<Privilege, Privilege> implements ISortingController<Privilege> {

    @Autowired
    private IPrivilegeService service;

    public PrivilegeController() {
        super(Privilege.class);
    }

    // API

    // find - all/paginated

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public List<Privilege> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                     @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public List<Privilege> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size
                                            , final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedInternal(page, size, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public List<Privilege> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    /**
     Changing the URL of an existing Resource
     i.e changing resources url, privileges can become only accessible via their role:
     - /api/privileges
     - /api/roles/7/privileges

     _ because the use of two values in request mapping is not possible to use @PathVariable("roleId") final String roleId
     _ because @PathVariable does not have an optional, and in this case it should be optional bc one of the mappings has it and the other doesnt
     _ A workaround is to replace the string for a Map, and in this way we have the option to inject this path variables
     _ and once we inject them its trivial to use in the actual implementation
     */
    @RequestMapping(value = { UmMappings.Plural.PRIVILEGES, "roles/{roleId}/privileges" }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public List<Privilege> findAll(@PathVariable final Map<String, String> pathVariables, final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public List<Privilege> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // find - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_PRIVILEGE_READ)
    public Privilege findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    // create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Um.Privileges.CAN_PRIVILEGE_WRITE)
    public void create(@RequestBody @Valid final Privilege resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    // update

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Um.Privileges.CAN_PRIVILEGE_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody final Privilege resource) {
        updateInternal(id, resource);
    }

    // delete

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Um.Privileges.CAN_PRIVILEGE_WRITE)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IPrivilegeService getService() {
        return service;
    }


}
