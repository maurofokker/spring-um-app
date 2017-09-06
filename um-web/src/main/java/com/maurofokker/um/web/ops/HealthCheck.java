package com.maurofokker.um.web.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * The purpose is to allow us to make our own decision whether or not the API is up and running or is down
 * i.e.
 * - check of persistence layer and make sure is ok
 * - check any sort of external dependency (caching layer)
 * - any kind of check that will determine if the system is up
 */
@Component
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        if (check()) {
            return Health.up().build();
        }
        return Health.outOfService().build();
    }

    /**
     * This is the Health logic
     * - still need to build the health object and the check it self
     * - is possible to inject a repository and do a read to see if system is up
     * @return
     */
    private boolean check() {
        return false;
    }
}
