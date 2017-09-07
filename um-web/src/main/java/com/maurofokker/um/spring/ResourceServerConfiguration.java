package com.maurofokker.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer // oauth2 to identify our Resource Server in the Oauth2 flow
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan({ "com.maurofokker.um.security" })
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /*
    The ResourceServerConfiguration holds everithing related to security configuration Oauth2 and others
    Spring has become more expresive in terms to configure things
    ResourceServerConfigurerAdapter and @EnableResourceServer are the only OAuth2 security configs, the rest
    are just Spring Security
    Transition from basic auth (UmJavaSecurityConfig.class) to OAuth (this class) is simple and not require to much work
     */

    /*
    info: because this is a single project there is no need to configure JwtAccessTokenConverter here (authorization server
    and resource server are living in the same spring context). In case the authorization and resource server were define
    separated, in this file (ResourceServerConfiguration) we must implement JwtAccessTokenConverter with the same sign-in key
    than the Authorization Server
    info 2: without configuration here the resource server is fine understanding Jwt Tokens signed by the authorization server
     */

    @Autowired
    private UserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    // global security concerns

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    // http level security concerns

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                //.antMatchers("/api/health").anonymous()
                .anyRequest().authenticated()       // secure everything
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
        http.headers().cacheControl().disable();
        // @formatter:on
    }
}
