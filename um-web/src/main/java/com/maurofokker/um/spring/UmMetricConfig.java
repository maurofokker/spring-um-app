package com.maurofokker.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.maurofokker.common.metric" })
public class UmMetricConfig {

    public UmMetricConfig() {
        super();
    }

}
