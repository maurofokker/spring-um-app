package com.maurofokker.um.service.main;

import com.maurofokker.common.persistence.service.IService;
import com.maurofokker.um.persistence.model.Privilege;
import com.maurofokker.um.service.IPrivilegeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class PrivilegeServiceIntegrationTest extends SecServiceIntegrationTest<Privilege> {

    @Autowired
    private IPrivilegeService privilegeService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        privilegeService.create(createNewEntity());
    }

    @Test(expected = DataAccessException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        privilegeService.create(createNewEntity(name));
        privilegeService.create(createNewEntity(name));
    }

    // template method

    @Override
    protected final IService<Privilege> getApi() {
        return privilegeService;
    }

    @Override
    protected final Privilege createNewEntity() {
        return new Privilege(randomAlphabetic(8));
    }

    // util

    protected final Privilege createNewEntity(final String name) {
        return new Privilege(name);
    }

    @Override
    protected void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final Privilege entity) {
        entity.setName(randomAlphabetic(6));
    }

}
