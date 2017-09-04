package com.maurofokker.um.web.hateoas;

import com.maurofokker.um.persistence.model.Role;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * RoleResource is wrapping existing role resource is not a replacement
 * This ResourceSupport approach is just providing  a simply thin wrapper around
 * resources and not replace the full thing
 */
public class RoleResource extends ResourceSupport {

    private final Role role;

    public RoleResource(final Role role) {
        this.role = role;

        //this.add(linkTo(RoleHateoasControllerSimple.class).withRel("roles"));
        this.add(linkTo(RoleHateoasController.class).withRel("roles"));
        // another type of link to see how powerful it is
        // - not linking to the entire controller but to a specific method of the controller
        //   pointing to the operation that is exposed via that method, in this case findOne(:id)
        //   accepting the id argument
        // - is specifying the REL Type (with withSelfRel() method), instead of using a custom REL Type,
        //   are using the self REL Type because the operation findOne() does map to the self REL Type
        //   seeing how it points to the role
        this.add(linkTo(methodOn(RoleHateoasController.class, role).findOne(role.getId())).withSelfRel());
    }

    //

    public Role getRole() {
        return role;
    }
}
