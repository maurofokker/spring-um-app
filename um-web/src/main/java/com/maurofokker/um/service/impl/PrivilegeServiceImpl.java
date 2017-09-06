package com.maurofokker.um.service.impl;

import com.maurofokker.common.persistence.service.AbstractService;
import com.maurofokker.um.persistence.dao.IPrivilegeJpaDao;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    @Autowired
    IPrivilegeJpaDao dao;

    @Autowired
    private CounterService counterService;

    public PrivilegeServiceImpl() {
        super();
    }

    // API

    // find

    /**
     * Add counter service to track how many times this method has been called and make available in metrics monitoring
     * increment of key "service.privilege.findByName" the syntax is layer.type_of_data_working_on.operation
     * the syntax is a string and could be anything but this is a clean way to track data
     * This could be done in the controller layer, also in the service layer, is possible to track any sort of metric
     * @param name
     * @return
     */
    @Override
    public Privilege findByName(final String name) {
        counterService.increment("service.privilege.findByName");
        return getDao().findByName(name);
    }

    // Spring

    @Override
    protected final IPrivilegeJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
