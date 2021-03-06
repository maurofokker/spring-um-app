package com.maurofokker.um.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer // oauth2
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // injecting the sign-in key using the Value annotation and providing the default value if we
    // ever want to configure externally
    @Value("${signing-key:oui214hmui23o4hm1pui3o2hp4m1o3h2m1o43}")
    private String signingKey;

    public AuthorizationServerConfiguration() {
        super();
    }

    // beans

    /*
    JwtAccessTokenConverter decodes and encodes JwtToken into OAuth information
    Bridges the gap between jwt as a token, having all of this information encoded inside the token, and
    what OAuth actually requires

    Info: using symmetrical cryptography, using signingkey to sign our tokens so when we're going to reach the resource
    server configuration (next configuration) we need to define the exact same JwtAccessTokenConverter. And more important
    is the need to use the exact same sign-in key, in order for the resurce server to be able to consume and to check the
    tokens that the authorization server will use

    Info 2: in this project is not necesary to do a lot of configuration bc the authorization server and the resorce server
    are living in the same spring context and so we are sharing this Bean. However if the authorization server would be in a
    different project, and that is a common way to set up (in practice)... then we would have to define this JWT AccessTokenConverter
    exactly the same both in the authorization server, as well as in the resource server
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey); // using symmetrical cryptography, using signingkey to sign our tokens
        return jwtAccessTokenConverter;
    }

    /*
    Using JwtTokenStore implementation that require an accessTokenConverter defined above
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary  //info: set as primary bc spring has 1 too and now we explicitly set to use this one
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true); // enable refresh tokens
        return tokenServices;
    }
    // config

    /*
    Wired of the authentication manager and tokenStore
    To assure that the endpoint uses the token store as well the authentication manager
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
        // @formatter:off
        endpointsConfigurer
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) // info: need to set the userDetailsService to the config endpoint
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .accessTokenConverter(accessTokenConverter()); // info: final config pointing to the same token covnerter above
        // @formatter:on
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("live-test")     // define a live-test (client id) client to work just with the live tests, used to hit token api
                .secret("H0l4MuNd0")                // working with a trusted client so define a psw,
                .authorizedGrantTypes("password", "refresh_token")   // using the password flow in the url &grant_type=password and adding "refresh_token for grant type
                .refreshTokenValiditySeconds(3600 * 24)  // refresh token valid for 24 hours
                .scopes("um-web", "read", "write", "trust")                   // scope and autoApprove define
                .autoApprove("um-web")
                .accessTokenValiditySeconds(3600)  // live-tests generate new access token always
                //
                .and()
                //
                .withClient("um")
                .secret("VXB0YWtlLUlyb24h")
                .authorizedGrantTypes("password", "refresh_token")
                .refreshTokenValiditySeconds(3600 * 24)
                .scopes("um-web", "read", "writes", "trust")
                .autoApprove("um-web")
                .accessTokenValiditySeconds(3600)
                ;
        // @formatter:on
    }

    /*
    To enable an endpoint to check access tokens once get one from the authorization server
    permitAll() means that the endpoint is gonna be public this is not the best way but is a proof of concept
    we could use "Is authenticated" instead
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
        super.configure(security);
    }
}
