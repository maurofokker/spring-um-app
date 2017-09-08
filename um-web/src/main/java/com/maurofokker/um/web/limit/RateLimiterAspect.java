package com.maurofokker.um.web.limit;

import com.google.common.util.concurrent.RateLimiter;
import com.maurofokker.um.util.limit.JoinPointToStringHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterAspect.class);

    // map to hold limiters
    private final ConcurrentHashMap<String, RateLimiter> limiters;

    public RateLimiterAspect() {
        this.limiters = new ConcurrentHashMap<>();
    }

    // logic for rate limit

    @Before("@annotation(limit)")
    public void rateLimit(JoinPoint jp, RateLimit limit) {
        String key = getOrCreateKey(jp, limit); // retrieve key
        // go into the map and get the limiter associated with it (or we create one)
        RateLimiter limiter = limiters.computeIfAbsent(key, (name -> RateLimiter.create(limit.value())));
        // acquire or using the limiter
        double delay = limiter.acquire();
        LOGGER.debug("Acquired rate limit permission ({} qps) for {} in {} seconds", limiter.getRate(), key, delay);
    }

    //

    private String getOrCreateKey(JoinPoint jp, RateLimit limit) {
        if( limit.key() == null ){
            return limit.key();
        }
        return JoinPointToStringHelper.toString(jp);
    }

}