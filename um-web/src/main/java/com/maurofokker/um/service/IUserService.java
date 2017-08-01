package com.maurofokker.um.service;

import com.maurofokker.common.persistence.service.IService;
import com.maurofokker.um.web.controller.dto.UserDto;

public interface IUserService extends IService<UserDto> {

    UserDto getCurrentUser();

}
