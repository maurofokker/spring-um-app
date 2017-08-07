package com.maurofokker.um.client;

import com.maurofokker.common.client.CommonPaths;
import com.maurofokker.common.web.IUriMapper;
import com.maurofokker.um.persistence.model.Role;
import com.maurofokker.um.persistence.model.User;
import com.maurofokker.um.persistence.model.Privilege;
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

    @Value("${http.sec.path}")
    private String secPath;

    @Autowired
    private CommonPaths commonPaths;

    @Autowired
    private IUriMapper uriMapper;

    // API

    public final String getContext() {
        return commonPaths.getServerRoot() + secPath;
    }

    public final String getRootUri() {
        return getContext() + "/api/";
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

}
