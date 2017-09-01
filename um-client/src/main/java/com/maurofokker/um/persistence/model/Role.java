package com.maurofokker.um.persistence.model;

import com.maurofokker.common.interfaces.INameableDto;
import com.maurofokker.common.persistence.model.INameableEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Entity
@XmlRootElement
public class Role implements INameableEntity, INameableDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    private String description;

    // @formatter:off
    @ManyToMany( /* cascade = { CascadeType.REMOVE }, */fetch = FetchType.EAGER)
    @JoinTable(joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRIV_ID", referencedColumnName = "PRIV_ID") })
    private Set<Privilege> privileges;
    // @formatter:on

    public Role() {
        super();
    }

    public Role(final String nameToSet, final String description) {
        super();
        name = nameToSet;
        this.description = description;
    }

    public Role(final String nameToSet) {
        this(nameToSet, randomAlphabetic(6));
    }

    public Role(final String nameToSet, final Set<Privilege> privilegesToSet) {
        this(nameToSet, randomAlphabetic(6), privilegesToSet);
    }

    public Role(final String nameToSet, final String description, final Set<Privilege> privilegesToSet) {
        super();
        name = nameToSet;
        this.description = description;
        privileges = privilegesToSet;
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

    @Override
    public void setName(final String nameToSet) {
        name = nameToSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(final Set<Privilege> privilegesToSet) {
        privileges = privilegesToSet;
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
        final Role other = (Role) obj;
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
