package com.maurofokker.um.persistence.dao;

import com.maurofokker.common.interfaces.IByNameApi;
import com.maurofokker.um.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {
    //
}
