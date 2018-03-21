package org.survey.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseInitBean implements InitializingBean {
    @Resource
    private UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("afterPropertiesSet");
        userRepository.save(new User("admin", "admin", "email", Role.ROLE_ADMIN));
    }
}
