package com.maurofokker.um.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
@Import({ // @formatter:off
        UmContextConfig.class,
        UmPersistenceJpaConfig.class,
        UmServiceConfig.class,
        UmWebConfig.class
}) // @formatter:on
public class UmApp extends SpringBootServletInitializer {
    // SpringBootServletInitializer da la posibilidad de hacer deploy de la de forma tradicional
    // al deshacerse de web.xml

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    // configuracion similar al web.xml
    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/api/*");

        final Map<String, String> params = new HashMap<String, String>();
        params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        params.put("contextConfigLocation", "org.spring.sec2.spring");
        params.put("dispatchOptionsRequest", "true");
        registration.setInitParameters(params);

        registration.setLoadOnStartup(1);
        return registration;
    }

    // Trae toda la configuracion al cargar el UmApp que carga el resto de configuraciones
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(UmApp.class);
    }

    // metodo que corre toda la app
    // con esto se puede hacer deploy con Spring Boot o de forma normal en otro web server
    public static void main(final String... args) {
        SpringApplication.run(UmApp.class, args);
    }

}
