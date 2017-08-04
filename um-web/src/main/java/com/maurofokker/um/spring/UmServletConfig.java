package com.maurofokker.um.spring;

import com.maurofokker.um.security.SimpleCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
@Configuration
public class UmServletConfig {

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

    /*
    @Bean
    @Order(1)
    public FilterRegistrationBean springSecurityFilterChain() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        final DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        filterRegBean.setFilter(delegatingFilterProxy);
        final List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        return filterRegBean;
    }
    */

    // INFO this is for relaxing CORS filter because rest server is running in different port than ui
    @Bean
    public SimpleCorsFilter simpleCorsFilter() {
        return new SimpleCorsFilter();
    }

}
