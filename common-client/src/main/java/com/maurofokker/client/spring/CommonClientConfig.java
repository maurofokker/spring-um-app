package com.maurofokker.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Configuration
@ComponentScan({
        "com.maurofokker.common.client",
        "com.maurofokker.client"
})
public class CommonClientConfig {

    public CommonClientConfig() {
        super();
    }
}
