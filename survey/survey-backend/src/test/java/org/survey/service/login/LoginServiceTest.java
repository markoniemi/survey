package org.survey.service.login;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.security.JwtToken;
import org.survey.service.user.UserService;

public class LoginServiceTest {
    @Mock
    UserService userService;
    private LoginServiceImpl loginService;
    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginService = new LoginServiceImpl();
        loginService.setUserService(userService);
        User user = new User("username", "password", "email", Role.ROLE_USER);
        Mockito.when(userService.findOne(Mockito.anyString())).thenReturn(user);
        Mockito.when(request.getHeader(JwtToken.AUTHORIZATION_HEADER)).thenReturn("token");
    }

    @Test
    public void login() throws AuthenticationException {
        User user = new User("username", "password", "email", Role.ROLE_USER);
        String token = loginService.login(user);
        Assert.assertNotNull(token);
    }

    @Test(expected = NotAuthorizedException.class)
    public void loginNoUser() throws AuthenticationException {
        Mockito.when(userService.findOne(Mockito.anyString())).thenReturn(null);
        User user = new User("no_user", "password", "email", Role.ROLE_USER);
        String token = loginService.login(user);
        Assert.assertNotNull(token);
    }

    @Test(expected = NotAuthorizedException.class)
    public void loginWrongPassword() throws AuthenticationException {
        User user = new User("username", "wrong_password", "email", Role.ROLE_USER);
        String token = loginService.login(user);
        Assert.assertNotNull(token);
    }
    @Test
    public void logout() throws AuthenticationException {
        loginService.logout(request);
    }
}
