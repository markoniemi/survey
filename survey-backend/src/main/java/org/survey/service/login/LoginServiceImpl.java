package org.survey.service.login;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.survey.model.user.User;
import org.survey.security.JwtToken;
import org.survey.service.user.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServiceImpl implements LoginService {
// TODO use either userRepository or autowired
//    @Resource
    @Autowired
    @Getter
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
