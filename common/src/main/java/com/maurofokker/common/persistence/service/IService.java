package com.maurofokker.common.persistence.service;

import com.maurofokker.common.interfaces.IByNameApi;
import com.maurofokker.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

    //

}
