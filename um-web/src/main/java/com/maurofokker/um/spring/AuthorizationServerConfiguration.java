package com.maurofokker.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer // oauth2
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthorizationServerConfiguration() {
        super();
    }

    /*
    For development is ok the inmemory token store but is better to use one that is persistent backed
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    // config

    /*
    Wired of the authentication manager and tokenStore
    To assure that the endpoint uses the token store as well the authentication manager
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
        endpointsConfigurer.tokenStore(tokenStore()).authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("live-test")     // define a live-test (client id) client to work just with the live tests, used to hit token api
                .secret("H0l4MuNd0")                // working with a trusted client so define a psw,
                .authorizedGrantTypes("password")   // using the password flow in the url &grant_type=password
                .scopes("um-web")                   // scope and autoApprove define
                .autoApprove("um-web")
                .accessTokenValiditySeconds(3600);  // live-tests generate new access token always
        // @formatter:on
    }
}
