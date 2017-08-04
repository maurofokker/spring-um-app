package com.maurofokker.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Configuration
@ComponentScan({"com.maurofokker.common.web"})
public class CommonWebConfig {

    public CommonWebConfig() { super(); }

}
