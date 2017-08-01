package com.maurofokker.common.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public final class RandomDataUtil {

    private RandomDataUtil() {
        throw new AssertionError();
    }

    // API

    public static String randomEmail() {
        return randomAlphabetic(6) + "@" + randomAlphabetic(4) + ".com";
    }

}
