package org.survey.service.user;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.survey.model.user.User;

//@WebService
@WebService(serviceName = "userService")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON})
@Path("/users")
public interface UserService {
    /**
     * @return all users from repository or an empty list in case of no items.
     */
    @GET
    User[] findAll();

    /**
     * Creates a user to repository.
     * 
     * @throws NullPointerException
     *             if the username is null
     * @throws IllegalArgumentException
     *             if the username is blank
     * @throws IllegalArgumentException
     *             if username already exists
     */
    @POST
    User create(@WebParam(name = "user") User user);

    /**
     * Updates a user in repository.
     * 
     * @throws NullPointerException
     *             if the user does not exist
     */
    @PUT
    User update(@WebParam(name = "user") User user);

    /**
     * @return user by username, or null if user does not exist
     */
    @GET
    @Path("/{username}")
    User findOne(@PathParam("username") @WebParam(name = "username") String username);

    /**
     * @return user by email, or null if user does not exist
     */
    @GET
    @Path("/email/{email}")
    User findByEmail(@PathParam("email") @WebParam(name = "email") String email);

    /**
     * @return true if a user by username exists.
     */
    @GET
    @Path("/exists/{username}")
    boolean exists(@PathParam("username") @WebParam(name = "username") String username);

    /**
     * Deletes a user by username.
     */
    @DELETE
    @Path("/{username}")
    void delete(@PathParam("username") @WebParam(name = "username") String username);

    /**
     * @return the count of users in repository.
     */
    @GET
    @Path("/count")
    long count();
}
