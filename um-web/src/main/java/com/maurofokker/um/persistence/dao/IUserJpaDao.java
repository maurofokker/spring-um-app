package com.maurofokker.um.persistence.dao;

import com.maurofokker.common.interfaces.IByNameApi;
import com.maurofokker.um.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserJpaDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, IByNameApi<User> {
    //
}