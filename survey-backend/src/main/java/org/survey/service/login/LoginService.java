package org.survey.service.login;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.survey.model.user.User;

@Path("/")
public interface LoginService {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    String login(User userToLogin) throws AuthenticationException;

    @POST
    @Path("/logout")
    void logout(HttpServletRequest request);
}