package com.maurofokker.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
                authorizeRequests().
                // antMatchers("/api/**"). // if you want a more explicit mapping here
                        anyRequest().
                authenticated().
                and().
                httpBasic().and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // @formatter:on
    }
    /*
     So first of all we are opening up this authorization request so that we can start mapping our request.
     And in this case we're not really mapping anything, we are saying that this configuration applies to any request,
     and we are saying that all of these requests need to be authenticated. We are then using the end to connect to our next bit of configuration,
     and we are also configuring the session management. Remember in the XML configuration, we had that
     configuration of statelessness, and we want to achieve the same thing here, we want spring security not to use any sort of session.
     And that's it, this is our full security configuration here
     */

}
