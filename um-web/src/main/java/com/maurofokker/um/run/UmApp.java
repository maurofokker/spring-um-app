package com.maurofokker.um.run;

import com.maurofokker.um.persistence.setup.MyApplicationContextInitializer;
import com.maurofokker.um.spring.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mgaldamesc on 25-07-2017.
 */
@SpringBootApplication(exclude = { // @formatter:off
        //SecurityAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class
}) // @formatter:on
/*
@Import({ // @formatter:off
        UmContextConfig.class,
        UmPersistenceJpaConfig.class,
        UmServiceConfig.class,
        UmWebConfig.class,
        UmServletConfig.class,
        UmJavaSecurityConfig.class
}) // @formatter:on
*/
public class UmApp extends SpringBootServletInitializer {
    // SpringBootServletInitializer da la posibilidad de hacer deploy de la de forma tradicional
    // al deshacerse de web.xml

    private final static Object[] CONFIGS = { // @formatter:off
            UmContextConfig.class,
            UmPersistenceJpaConfig.class,
            UmServiceConfig.class,
            UmWebConfig.class,
            UmServletConfig.class,

            UmMetricConfig.class,

            //UmJavaSecurityConfig.class, // info: basic auth

            UmApp.class,

            ResourceServerConfiguration.class,
            AuthorizationServerConfiguration.class
    }; // @formatter:on

    // Trae toda la configuracion al cargar el UmApp que carga el resto de configuraciones
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        //return application.sources(UmApp.class);
        //return application.initializers(new MyApplicationContextInitializer()).sources(UmApp.class);
        return application.sources(CONFIGS).initializers(new MyApplicationContextInitializer());
    }

    // metodo que corre toda la app
    // con esto se puede hacer deploy con Spring Boot o de forma normal en otro web server
    public static void main(final String... args) {
        //SpringApplication.run(UmApp.class, args);

        //new SpringApplicationBuilder(UmApp.class).initializers(new MyApplicationContextInitializer()).run(args);

        final SpringApplication springApplication = new SpringApplication(CONFIGS);
        springApplication.addInitializers(new MyApplicationContextInitializer());
        springApplication.run(args);
    }

}
