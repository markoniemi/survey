package org.survey.user.bean;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

/**
 * Test UsersBean. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class UsersBeanTest {
	UsersBean usersBean;
	@Autowired
	UserService userService;

	@Before
	public void setUp() {
		userService
				.create(new User("admin", "admin", "admin", Role.ROLE_USER));
		usersBean = new UsersBeanMock();
		usersBean.setUserService(userService);
		usersBean.initialize();
	}

	@After
	public void tearDown() {
		for (User user : userService.findAll()) {
            userService.delete(user.getUsername());
        }
	}

	@Test
	public void getUsers() {
	    List<User> users = usersBean.getUsers();
		Assert.assertEquals(1, users.size());
		usersBean.getUsers();
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void getUsersWithUpdate() {
	    List<User> users = usersBean.getUsers();
		Assert.assertEquals(1, users.size());
		User createdUser = new User("user", "password", "email", Role.ROLE_USER);
		usersBean.getUserService().create(createdUser);
		Assert.assertEquals(2, usersBean.getUserService().findAll().length);
		// fake a page transition
		usersBean = new UsersBeanMock();
		usersBean.setUserService(userService);
		usersBean.initialize();
		users = usersBean.getUsers();
		Assert.assertEquals(2, users.size());
	}

	@Test
	public void deleteUser() throws Exception {
		User createdUser = new User("user", "password", "email", Role.ROLE_USER);
		usersBean.getUserService().create(createdUser);
		((UsersBeanMock)usersBean).setRequestParameter("user");
		usersBean.deleteUser();
		Assert.assertEquals(1, usersBean.getUserService().count());
	}

	private class UsersBeanMock extends UsersBean {
//        @Delegate
        BeanTestHelper beanTestHelper = new BeanTestHelper();

        @Override
        protected String getRequestParameter(String parameterName) {
            return beanTestHelper.getRequestParameter();
        }

        public void setRequestParameter(String requestParameter) {
            beanTestHelper.setRequestParameter(requestParameter);
        }
	}
}
