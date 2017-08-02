package com.maurofokker.um.persistence.dao;

import com.maurofokker.common.interfaces.IByNameApi;
import com.maurofokker.um.persistence.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public interface IPrincipalJpaDao extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, IByNameApi<Principal> {
}
