package com.maurofokker.common.util.order;

import com.google.common.collect.Ordering;
import com.maurofokker.common.interfaces.IWithName;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public class OrderByName<T extends IWithName> extends Ordering<T> {

    public OrderByName() {
        super();
    }

    @Override
    public int compare(T left, T right) {
        return left.getName().compareTo(right.getName());
    }
}
