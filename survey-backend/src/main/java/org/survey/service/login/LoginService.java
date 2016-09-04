package org.survey.service.login;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.survey.model.user.User;

public interface LoginService {

    String login(User userToLogin) throws AuthenticationException;

    void logout(HttpServletRequest request);

}