package com.maurofokker.common.util.order;

import com.google.common.collect.Ordering;
import com.maurofokker.common.persistence.model.INameableEntity;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public class OrderByNameIgnoreCase<T extends INameableEntity> extends Ordering<T> {

    public OrderByNameIgnoreCase() {
        super();
    }

    @Override
    public int compare(T left, T right) {
        return left.getName().compareTo(right.getName());
    }
}
