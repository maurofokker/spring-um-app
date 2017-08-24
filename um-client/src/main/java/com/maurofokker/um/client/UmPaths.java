package com.maurofokker.um.client;

import com.maurofokker.common.client.CommonPaths;
import com.maurofokker.common.web.IUriMapper;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.persistence.model.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by mgaldamesc on 03-08-2017.
 */
@Component
@Profile("client")
public final class UmPaths {
    private final Logger logger = LoggerFactory.getLogger(UmPaths.class);

    @Value("${http.sec.path}")
    private String secPath;

    @Value("${http.oauthPath}")
    private String oauthPath;

    @Autowired
    private CommonPaths commonPaths;

    @Autowired
    private IUriMapper uriMapper;

    // API

    public final String getContext() {
        String context = commonPaths.getServerRoot() + secPath;
        logger.info("App context is {}", context);
        return context;
    }

    public final String getRootUri() {
        String rootUri = getContext() + "/api/";
        logger.info("Root uri is {}", rootUri);
        return rootUri;
    }

    public final String getUserUri() {
        return getRootUri() + uriMapper.getUriBase(User.class);
    }

    public final String getRoleUri() {
        return getRootUri() + uriMapper.getUriBase(Role.class);
    }

    public final String getPrivilegeUri() {
        return getRootUri() + uriMapper.getUriBase(Privilege.class);
    }

    public final String getAuthenticationUri() {
        return getRootUri() + "authentication";
    }

    public final String getLoginUri() {
        return getContext() + "/j_spring_security_check";
    }

    public final String getPath() {
        return secPath;
    }

    public final String getOauthPath() {
        return oauthPath;
    }
}
