package com.maurofokker.um.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
//@Configuration
@ComponentScan("com.maurofokker.um.security")
@ImportResource({
        "classpath*:umSecurityConfig.xml"
})
public class UmSecurityConfig {
    public UmSecurityConfig() {
        super();
    }
}
