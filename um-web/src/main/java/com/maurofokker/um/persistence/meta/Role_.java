package com.maurofokker.um.persistence.meta;


import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.persistence.model.Role;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

    public static volatile SingularAttribute<Role, Long> id;
    public static volatile SingularAttribute<Role, String> name;
    public static volatile SetAttribute<Role, Privilege> privileges;

}
