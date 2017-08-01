package com.maurofokker.um.persistence.model;

import com.maurofokker.common.interfaces.INameableDto;
import com.maurofokker.common.persistence.model.INameableEntity;
import com.maurofokker.um.web.controller.dto.UserDto;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
@Entity
@XmlRootElement
public class Principal implements INameableEntity, INameableDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRINCIPAL_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column( /* nullable = false */)
    private Boolean locked;

    // @formatter:off
    @ManyToMany( /* cascade = { CascadeType.REMOVE }, */fetch = FetchType.EAGER)
    @JoinTable(joinColumns = { @JoinColumn(name = "PRINCIPAL_ID", referencedColumnName = "PRINCIPAL_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
    @XStreamImplicit
    private Set<Role> roles;
    // @formatter:on

    public Principal() {
        super();

        locked = false;
    }

    public Principal(final String nameToSet, final String passwordToSet, final Set<Role> rolesToSet) {
        super();

        name = nameToSet;
        password = passwordToSet;
        roles = rolesToSet;
    }

    public Principal(final UserDto userDto) {
        super();

        name = userDto.getName();
        email = userDto.getEmail();
        password = userDto.getPassword();
        roles = userDto.getRoles();
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

    public void setEmail(final String email) {
        this.email = email;
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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(final Boolean lockedToSet) {
        locked = lockedToSet;
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
        final Principal other = (Principal) obj;
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
