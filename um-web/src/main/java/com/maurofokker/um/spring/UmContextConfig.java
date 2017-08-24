package com.maurofokker.um.spring;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("classpath*:umContextConfig.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource({
                    "classpath:env-${envTarget:dev}.properties"
                    //,"classpath:web-${webTarget:local}.properties"
                })
public class UmContextConfig {

    public UmContextConfig() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }

}
