package org.survey.security;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

/**
 * Test UserRepositoryAuthenticationProvider. Must use SpringJUnit4ClassRunner
 * to enable spring injection. Loaded Spring configuration is defined by
 * ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class UserRepositoryAuthenticationProviderTest {
	@Resource
	UserService userService;

	@Before
	public void setUp() {
		userService
				.create(new User("test", "test", "test", Role.ROLE_ADMIN));
	}

	@After
	public void tearDown() {
		userService.delete("test");
	}

	@Test
	public void authenticate() {
		UserRepositoryAuthenticationProvider authenticationProvider = new UserRepositoryAuthenticationProvider();
		authenticationProvider.userService = userService;
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"test", "test");
		Authentication authenticationFromProvider = authenticationProvider
				.authenticate(authentication);
		Assert.assertNotNull(authenticationFromProvider);
		Assert.assertNotNull(authenticationFromProvider.getAuthorities());
		Assert.assertEquals(1, authenticationFromProvider.getAuthorities()
				.size());
		Assert.assertTrue(authenticationFromProvider.getAuthorities().contains(
				new SimpleGrantedAuthority("ROLE_ADMIN")));
	}

	@Test
	public void authenticateWithInvalidUsername() {
		UserRepositoryAuthenticationProvider authenticationProvider = new UserRepositoryAuthenticationProvider();
		authenticationProvider.userService = userService;
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"invalid", "invalid");
		Authentication authenticationFromProvider = authenticationProvider
				.authenticate(authentication);
		Assert.assertNull(authenticationFromProvider);
	}

	@Test
	public void authenticateWithInvalidPassword() {
		UserRepositoryAuthenticationProvider authenticationProvider = new UserRepositoryAuthenticationProvider();
		authenticationProvider.userService = userService;
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"test", "invalid");
		Authentication authenticationFromProvider = authenticationProvider
				.authenticate(authentication);
		Assert.assertNull(authenticationFromProvider);
	}
}
