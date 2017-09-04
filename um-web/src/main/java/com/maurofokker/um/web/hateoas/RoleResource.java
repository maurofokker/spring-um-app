package com.maurofokker.um.web.hateoas;

import com.maurofokker.um.persistence.model.Role;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * RoleResource is wrapping existing role resource is not a replacement
 * This ResourceSupport approach is just providing  a simply thin wrapper around
 * resources and not replace the full thing
 */
public class RoleResource extends ResourceSupport {

    private final Role role;

    public RoleResource(final Role role) {
        this.role = role;

        this.add(linkTo(RoleHateoasControllerSimple.class).withRel("roles"));
    }

    //

    public Role getRole() {
        return role;
    }
}
