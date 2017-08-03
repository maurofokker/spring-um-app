package com.maurofokker.client.template;

import com.maurofokker.common.interfaces.IDto;
import com.maurofokker.common.interfaces.IOperations;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public interface IRawClientTemplate<T extends IDto> extends IOperations<T>, ITemplateWithUri<T> {

    //

    String getUri();

    // create

    T create(final T resource, final Pair<String, String> credentials);

    T findOne(final long id, final Pair<String, String> credentials);

}