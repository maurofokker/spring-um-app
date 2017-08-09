package com.maurofokker.client.template;

import com.maurofokker.common.interfaces.IDto;

/**
 * Created by mgaldamesc on 09-08-2017.
 */
public interface IRestClientWithUri<T extends IDto> extends IReadOnlyTemplateWithUri<T> {

    // create

    String createAsUri(final T resource);

}
