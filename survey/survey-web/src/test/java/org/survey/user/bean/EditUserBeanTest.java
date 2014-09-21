package org.survey.user.bean;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.user.bean.EditUserBean;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.repository.UserRepository;
import org.survey.user.service.UserService;

/**
 * Test EditUserBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class EditUserBeanTest {
    EditUserBean editUserBean;
    @Autowired
    UserRepository userRepository;
    @Autowired
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
        void showMessage(String id, String messageKey) {
            beanTestHelper.showMessage(id, messageKey);
        }
    }
}
