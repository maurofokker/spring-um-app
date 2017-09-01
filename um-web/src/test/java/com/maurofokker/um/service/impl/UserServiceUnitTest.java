package com.maurofokker.um.service.impl;

import com.google.common.collect.Lists;
import com.maurofokker.test.common.service.AbstractServiceUnitTest;
import com.maurofokker.um.common.FixtureEntityFactory;
import com.maurofokker.um.persistence.dao.IUserJpaDao;
import com.maurofokker.um.persistence.model.User;
import org.junit.Before;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceUnitTest extends AbstractServiceUnitTest<User> {

    UserServiceImpl instance;

    private IUserJpaDao daoMock;

    // fixtures

    @Override
    @Before
    public final void before() {
        instance = new UserServiceImpl();

        daoMock = mock(IUserJpaDao.class);
        when(daoMock.save(any(User.class))).thenReturn(new User());
        when(daoMock.findAll()).thenReturn(Lists.<User> newArrayList());
        instance.dao = daoMock;
        super.before();
    }

    // get

    // mocking behavior

    @Override
    protected final User configureGet(final long id) {
        final User entity = new User();
        entity.setId(id);
        when(daoMock.findOne(id)).thenReturn(entity);
        return entity;
    }

    // template method

    @Override
    protected final UserServiceImpl getApi() {
        return instance;
    }

    @Override
    protected final JpaRepository<User, Long> getDAO() {
        return daoMock;
    }

    @Override
    protected final User createNewEntity() {
        return FixtureEntityFactory.createNewUser();
    }

    @Override
    protected void changeEntity(final User entity) {
        entity.setPassword(randomAlphabetic(8));
    }

}
