package com.maurofokker.um.web.controller.dto;

import com.maurofokker.common.interfaces.INameableDto;
import com.maurofokker.common.persistence.model.INameableEntity;
import com.maurofokker.um.persistence.model.Principal;
import com.maurofokker.um.persistence.model.Role;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by mgaldamesc on 31-07-2017.
 */
@XmlRootElement
public class UserDto implements INameableEntity, INameableDto {

    private Long id;

    @Size(min = 2, max = 30)
    @NotNull                                            // validation api
    private String name;

    @Email                                              // hibernate valdiation api
    @NotNull
    private String email;

    // INFO: logical bound constraints i.e. from 0 to 99
    @Min(0)
    @Max(99)
    private int age;

    private String password;


    /* Marshalling */
    // - note: this gets rid of the collection entirely
    // - note: this requires: xstream.addDefaultImplementation( java.util.HashSet.class, PersistentSet.class );
    // @XStreamConverter( value = HibernateCollectionConverter.class )
    private Set<Role> roles;

    private LocalDateTime activeSince;

    public UserDto() {
        super();
    }

    public UserDto(final String nameToSet, final String passwordToSet, final Set<Role> rolesToSet) {
        super();

        name = nameToSet;
        password = passwordToSet;
        roles = rolesToSet;
    }

    public UserDto(final Principal principal) {
        super();

        name = principal.getName();
        email = principal.getEmail();
        roles = principal.getRoles();
        id = principal.getId();

        activeSince = LocalDateTime.now();
    }

    // API

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long idToSet) {
        id = idToSet;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String nameToSet) {
        name = nameToSet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String emailToSet) {
        email = emailToSet;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String passwordToSet) {
        password = passwordToSet;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> rolesToSet) {
        roles = rolesToSet;
    }

    public LocalDateTime getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(final LocalDateTime activeSince) {
        this.activeSince = activeSince;
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserDto other = (UserDto) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).toString();
    }

}
