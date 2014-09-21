package org.survey.user.bean;

import javax.annotation.PostConstruct;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

/**
 * ApplicationInitializationBean is an application scoped bean, and is therefore
 * created when the application is created. It initializes the data to database.
 */
@Component
@Scope("singleton")
public class ApplicationInitializationBean {
    @Setter
    @Autowired
    private transient UserService userService;

    @PostConstruct
    public void initialize() {
        userService.create(new User("admin", "admin", "admin@test.com",
                Role.ROLE_ADMIN));
    }
}
