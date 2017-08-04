package com.maurofokker.um.spring;

import com.maurofokker.client.spring.CommonClientConfig;
import com.maurofokker.common.spring.CommonWebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Configuration
@ComponentScan({ "com.maurofokker.um.client" })
@Import({ CommonClientConfig.class, CommonWebConfig.class })
public class UmClientConfig {
    public UmClientConfig() {
        super();
    }
}
