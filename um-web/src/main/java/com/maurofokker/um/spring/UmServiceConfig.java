package com.maurofokker.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.maurofokker.um.service" })
public class UmServiceConfig {

    public UmServiceConfig() {
        super();
    }

    // beans

}
