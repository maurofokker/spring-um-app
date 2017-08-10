package com.maurofokker.client.template;

import com.maurofokker.common.interfaces.IDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public interface IReadOnlyTemplateWithUri<T extends IDto> {

    // find - one

    T findOneByUri(final String uri);

    // find - all

    List<T> findAllByUri(final String uri);

}
