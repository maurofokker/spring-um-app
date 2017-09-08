package com.maurofokker.um.web.limit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * @return rate limit in queries per second
     */
    int value();

    /**
     * key is optional in case the need to group together sets of operations
     * and limit them together (sets of operations using the same key)
     * @return rate limiter identifier (optional)
     */
    String key() default "";

}