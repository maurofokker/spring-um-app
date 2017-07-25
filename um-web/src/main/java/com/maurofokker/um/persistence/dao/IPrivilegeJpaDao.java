package com.maurofokker.um.persistence.dao;

import com.maurofokker.common.interfaces.IByNameApi;
import com.maurofokker.um.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrivilegeJpaDao extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege>, IByNameApi<Privilege> {
    //
}
