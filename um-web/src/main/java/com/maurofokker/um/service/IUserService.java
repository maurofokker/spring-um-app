package com.maurofokker.um.service;

import com.maurofokker.common.persistence.service.IService;
import com.maurofokker.um.persistence.model.User;

public interface IUserService extends IService<User> {

    //UserDto getCurrentUser();

    User createSlow(final User user);

}
