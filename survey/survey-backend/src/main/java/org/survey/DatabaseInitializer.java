package org.survey;

import javax.annotation.PostConstruct;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

@Slf4j
public class DatabaseInitializer {

    @Setter
    @Autowired
    private transient UserService userService;

    @PostConstruct
    public void initialize() {
        log.debug("DatabaseInitializer.initialize");
        try {
            userService.create(new User("admin", "admin", "admin@test.com", Role.ROLE_ADMIN));
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
        }
    }
}
