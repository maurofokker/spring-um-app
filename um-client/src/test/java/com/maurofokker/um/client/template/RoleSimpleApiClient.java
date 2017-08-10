package com.maurofokker.um.client.template;


import com.maurofokker.common.spring.util.Profiles;
import com.maurofokker.um.persistence.model.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile(Profiles.CLIENT)
public final class RoleSimpleApiClient extends GenericSimpleApiClient<Role> {

    public RoleSimpleApiClient() {
        super(Role.class);
    }

    @Override
    public String getUri() {
        return paths.getRoleUri();
    }
}
