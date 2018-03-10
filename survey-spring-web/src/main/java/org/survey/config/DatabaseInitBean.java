package org.survey.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DatabaseInitBean implements InitializingBean {
    @Resource
    private UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("afterPropertiesSet");
        userRepository.save(new User("admin", "admin", "email", Role.ROLE_ADMIN));
    }
}
