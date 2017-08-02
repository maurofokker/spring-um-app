package com.maurofokker.um.service;

import com.maurofokker.common.persistence.service.IService;
import com.maurofokker.um.persistence.model.Principal;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
