package com.maurofokker.um.web.hateoas;

import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.service.IRoleService;
import com.maurofokker.um.util.UmMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = UmMappings.Hateoas.ROLES)
public class RoleHateoasControllerSimple {

    @Autowired
    private IRoleService service;

    // API - find - one

    /**
     * With Spring Hateoas ResourceSupport is the same as calling the service to get a resource
     * but wrap the new RoleResource support when returnin it
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RoleResource findOne(@PathVariable("id") final Long id) {
        final Role entity = service.findOne(id);
        return new RoleResource(entity);
    }
}
