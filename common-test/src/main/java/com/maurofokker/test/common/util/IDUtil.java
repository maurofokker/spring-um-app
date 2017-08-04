package com.maurofokker.test.common.util;

import java.util.Random;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
public final class IDUtil {

    private IDUtil() {
        throw new AssertionError();
    }

    // API

    public final static String randomPositiveLongAsString() {
        return Long.toString(randomPositiveLong());
    }

    public final static String randomNegativeLongAsString() {
        return Long.toString(randomNegativeLong());
    }

    public final static long randomPositiveLong() {
        long id = new Random().nextLong() * 10000;
        id = (id < 0) ? (-1 * id) : id;
        return id;
    }

    public final static long randomNegativeLong() {
        long id = new Random().nextLong() * 10000;
        id = (id > 0) ? (-1 * id) : id;
        return id;
    }

}
