package org.survey.bean;

import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;
import org.survey.service.user.UserService;

/**
 * Test EditUserBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class EditUserBeanTest {
    EditUserBean editUserBean;
    @Resource
    UserRepository userRepository;
    @Resource
    UserService userService;

    @Before
    public void setUp() throws MalformedURLException {
        editUserBean = new EditUserBeanMock();
        editUserBean.setUserService(userService);
    }

    @After
    public void tearDown() {
        User[] users = userService.findAll();
        for (User user : users) {
            userService.delete(user.getUsername());
        }
    }

    @Ignore
    @Test
    public void editUser() {
        editUserBean.editUser();
        Assert.assertNull(editUserBean.getUser());
    }

    @Test
    public void saveUser() {
        editUserBean.addUser();
        // create user 
        User createdUser = new User("user", "password", "email", Role.ROLE_USER);
        editUserBean.setUser(createdUser);
        editUserBean.setRepeatedPassword(createdUser.getPassword());
        editUserBean.saveUser();
        User userFromDatabase = userRepository.findByUsername(createdUser.getUsername());
        Assert.assertNotNull("registered user was not added to database", userFromDatabase);
        // initialize gets the user from database
        ((EditUserBeanMock)editUserBean).setRequestParameter("user");
        editUserBean.editUser();
        // edit user
        editUserBean.getUser().setPassword("newpassword");
        editUserBean.setRepeatedPassword("newpassword");
        editUserBean.getUser().setEmail("newemail@test.com");
        String result = editUserBean.saveUser();
        Assert.assertEquals("editUserBean.update returned an unexpected value", "userSaved", result);
        // verify
        userFromDatabase = userRepository.findByUsername(createdUser.getUsername());
        Assert.assertNotNull("registered user was not added to database", userFromDatabase);
        Assert.assertEquals("newpassword", userFromDatabase.getPassword());
        Assert.assertEquals("newemail@test.com", userFromDatabase.getEmail());
    }

    @Test
    public void saveUserWithUnequalRepeatedPassword() {
        // create user 
        User createdUser = new User("user", "password", "email", Role.ROLE_USER);
        editUserBean.setUser(createdUser);
        editUserBean.setRepeatedPassword(createdUser.getPassword());
        editUserBean.saveUser();
        User userFromDatabase = userRepository.findByUsername(createdUser.getUsername());
        Assert.assertNotNull("registered user was not added to database", userFromDatabase);
        // initialize gets the user from database
        ((EditUserBeanMock)editUserBean).setRequestParameter("user");
        editUserBean.editUser();
        // edit user
        editUserBean.getUser().setPassword("newpassword");
        editUserBean.setRepeatedPassword("wrongPassword");
        editUserBean.getUser().setEmail("newemail@test.com");
        String result = editUserBean.saveUser();
        Assert.assertNull("editUserBean.update returned an unexpected value", result);
        // verify
        userFromDatabase = userRepository.findByUsername(createdUser.getUsername());
        Assert.assertEquals("User should not have been updated", createdUser.getPassword(),
                userFromDatabase.getPassword());
    }

    private class EditUserBeanMock extends EditUserBean {
        BeanTestHelper beanTestHelper = new BeanTestHelper();

        public void setRequestParameter(String requestParameter) {
            beanTestHelper.setRequestParameter(requestParameter);
        }

        @Override
        protected String getRequestParameter(String parameterName) {
            return beanTestHelper.getRequestParameter();
        }

        @Override
        void showMessage(String id, String messageKey, Exception e) {
            beanTestHelper.showMessage(id, messageKey);
        }
    }
}
