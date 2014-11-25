package org.survey;

import javax.annotation.PostConstruct;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

public class DatabaseInitializer {

    @Setter
    @Autowired
    private transient UserService userService;

    @PostConstruct
    public void initialize() {
        userService.create(new User("admin", "admin", "admin@test.com",
                Role.ROLE_ADMIN));
    }
}
