package org.survey.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DatabaseInitializer {
    @Resource
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() throws Exception {
        log.debug("DatabaseInitializer.initialize");
        userRepository.save(new User("admin1", "admin", "email", Role.ROLE_ADMIN));
    }
}
