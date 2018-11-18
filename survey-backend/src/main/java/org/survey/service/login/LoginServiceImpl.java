package org.survey.service.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;

import org.survey.model.user.User;
import org.survey.security.JwtToken;
import org.survey.service.user.UserService;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginServiceImpl implements LoginService {
    @Resource
    @Setter
    private UserService userService;

    @Override
    public String login(User userToLogin) {
        User user = userService.findOne(userToLogin.getUsername());
        if (user == null) {
            throw new NotAuthorizedException("Login error");
        }
        if (user.getPassword().equals(userToLogin.getPassword())) {
            log.debug("Username: {} logged in.", user.getUsername());
            return new JwtToken(user).getToken();
        } else {
            throw new NotAuthorizedException("Login error");
        }
    }

    @Override
    public void logout(@Context HttpServletRequest request) {
        String authenticationToken = (String) request.getHeader(JwtToken.AUTHORIZATION_HEADER);
        log.debug(authenticationToken);
    }
}
