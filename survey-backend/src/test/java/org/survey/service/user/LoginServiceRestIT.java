package org.survey.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ServiceRestTestConfig;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.login.LoginService;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;

/**
 * Test PersonManagement using WebService. Spring injects userService with
 * WebService. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 * inheritLocations prevents UserServiceTestBase from loading spring config.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ServiceRestTestConfig.class))
public class LoginServiceRestIT {
    @Resource
    LoginService loginService;

    @Test
    public void login() throws AuthenticationException {
        loginService.login(new User("admin", "admin", "admin@email", Role.ROLE_ADMIN));
    }
}
