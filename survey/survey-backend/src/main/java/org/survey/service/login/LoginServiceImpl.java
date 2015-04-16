package org.survey.service.login;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.survey.model.user.User;
import org.survey.security.JwtToken;
import org.survey.service.user.UserService;

@Slf4j
@Path("/")
public class LoginServiceImpl implements LoginService {
// TODO use either userRepository or autowired
//    @Resource
    @Autowired
    @Getter
    @Setter
    private UserService userService;

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public String login(User userToLogin) throws AuthenticationException {
        User user = userService.findOne(userToLogin.getUsername());
        if (user == null) {
            throw new AuthenticationException("Login error");
        }
        if (user.getPassword().equals(userToLogin.getPassword())) {
            log.debug("Username: {} logged in.", user.getUsername());
            return new JwtToken(user).getToken();
        } else {
            throw new AuthenticationException("Login error");
        }
    }

    @Override
    @POST
    @Path("/logout")
    public void logout(@Context HttpServletRequest request) {
        String authenticationToken = (String) request.getHeader(JwtToken.AUTHORIZATION_HEADER);
        log.debug(authenticationToken);
    }
}
