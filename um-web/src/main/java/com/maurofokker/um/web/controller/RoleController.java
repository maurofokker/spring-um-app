package com.maurofokker.um.web.controller;

import com.maurofokker.common.util.QueryConstants;
import com.maurofokker.common.web.controller.AbstractController;
import com.maurofokker.common.web.controller.ISortingController;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.service.IRoleService;
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
import java.util.List;

@Controller
@RequestMapping(value = UmMappings.ROLES)
public class RoleController extends AbstractController<Role> implements ISortingController<Role> {

    @Autowired
    private IRoleService service;

    public RoleController() {
        super(Role.class);
    }

    // API

    // find - all/paginated

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_ROLE_READ)
    public List<Role> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
                                                @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder
                                                , final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_ROLE_READ)
    public List<Role> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size
                                , final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedInternal(page, size, uriBuilder, response);
    }

    @Override
    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_ROLE_READ)
    public List<Role> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Um.Privileges.CAN_ROLE_READ)
    public List<Role> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // find - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*")  // deals with cross origin and we can avoid using filter bean
    @Secured(Um.Privileges.CAN_ROLE_READ)
    public Role findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    // create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Um.Privileges.CAN_ROLE_WRITE)
    public void create(@RequestBody final Role resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    // update

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Um.Privileges.CAN_ROLE_WRITE)
    public void update(@PathVariable("id") final Long id, @RequestBody final Role resource) {
        updateInternal(id, resource);
    }

    // delete

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Um.Privileges.CAN_ROLE_WRITE)
    public void delete(@PathVariable("id") final Long id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IRoleService getService() {
        return service;
    }

}
