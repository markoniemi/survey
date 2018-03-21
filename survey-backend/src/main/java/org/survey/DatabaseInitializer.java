package org.survey;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseInitializer {

    @Setter
    @Resource(name = "userServiceBean")
    private UserService userService;

    @PostConstruct
    public void initialize() {
        log.debug("DatabaseInitializer.initialize");
        try {
            userService.create(new User("admin", "admin", "admin@test.com", Role.ROLE_ADMIN));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
