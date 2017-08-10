package com.maurofokker.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.maurofokker.um.security")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  // enable @Secured in controller and Pre - Post security annotation
public class UmJavaSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // this is ../security/MyUserDetailsService.java

    // same config than unSecurityConfig.xml
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    // HTTP element that we had in our XML configuration
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        // @formatter:off
        http.
                authorizeRequests().                    // opening up this authorization request so that we can start mapping our request
                // regexMatchers("^login.*").permitAll() // if you want to use regular expression and to allow to access bc login is unsecure
                // antMatchers("/api/**").              // if you want a more explicit mapping here
                anyRequest().                           // not mapping anything, this configuration applies to any request
                                                        // before we have url security level
                authenticated().                        // all of these requests need to be authenticated
                and().                                  // using the and to connect to our next bit of configuration
                httpBasic().and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS). // configuring the session management
                and().
                csrf().disable()

        ;
        // @formatter:on
    }
    /*
     In the XML configuration, we had that configuration of statelessness, and we want to achieve the same thing here,
     we want spring security not to use any sort of session.
     */

}
