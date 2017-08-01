package com.maurofokker.common.util.order;

import com.google.common.collect.Ordering;
import com.maurofokker.common.interfaces.IWithId;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public final class OrderById<T extends IWithId> extends Ordering<T> {

    public OrderById() {
    }

    @Override
    public int compare(final T left, final T right) {
        return left.getId().compareTo(right.getId());
    }
}
