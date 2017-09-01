package com.maurofokker.um.persistence.setup;

import com.google.common.base.Preconditions;
import com.maurofokker.common.persistence.event.BeforeSetupEvent;
import com.maurofokker.um.web.controller.AuthenticationController;
import com.maurofokker.um.web.controller.PrivilegeController;
import com.maurofokker.um.web.controller.RoleController;
import com.maurofokker.um.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public final class SpringVerificationsBeforeSetupListener implements ApplicationListener<BeforeSetupEvent> {
    // private final Logger logger = LoggerFactory.getLogger(SpringVerificationsBeforeSetupListener.class);

    @Autowired
    ApplicationContext context;

    public SpringVerificationsBeforeSetupListener() {
        super();
    }

    // API

    @Override
    public final void onApplicationEvent(final BeforeSetupEvent event) {
        Preconditions.checkNotNull(context.getBean(PrivilegeController.class));
        Preconditions.checkNotNull(context.getBean(RoleController.class));
        Preconditions.checkNotNull(context.getBean(UserController.class));
        Preconditions.checkNotNull(context.getBean(AuthenticationController.class));
    }

}
