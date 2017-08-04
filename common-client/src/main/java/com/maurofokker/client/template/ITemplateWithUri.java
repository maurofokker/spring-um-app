package com.maurofokker.client.template;

import com.maurofokker.common.interfaces.IDto;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public interface ITemplateWithUri<T extends IDto> extends IReadOnlyTemplateWithUri<T> {

    // create

    String createAsUri(final T resource, final Pair<String, String> credentials);

    String createAsUri(final T resource);

}
