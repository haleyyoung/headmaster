package edu.lmu.cs.headmaster.ws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.lmu.cs.headmaster.ws.domain.User;

/**
 * JAX-RS user service.
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UserService {

    /**
     * Possible service error messages.
     */
    String USER_OVERSPECIFIED = "user.overspecified";
    String USER_INCONSISTENT = "user.inconsistent";

    /**
     * Returns all known users. Only users with the HEADMASTER role should be
     * allowed to call this URI.
     * 
     * @return the known users
     */
    @GET
    List<User> getUsers();

    /**
     * Creates a user for which the server will generate the id.
     * 
     * @param user
     *            the user object to create. The user must have a null id. The
     *            initial password, if any, must be placed in the newPassword
     *            property and not password.
     * 
     * @return A response with HTTP 201 on success, or a response with HTTP 400
     *         and message <code>user.overspecified</code> if the user's
     *         id is not null.
     */
    @POST
    Response createUser(User user);

    /**
     * Supposed to save the representation of the user with the given id.
     * Inconsistent data should result in HTTP 400, while a successful PUT
     * should return Response.noContent.
     * 
     * @param id
     *            the id of the user to save.
     * 
     * @return A response with HTTP 204 no content on success, or a response
     *         with HTTP 400 and message <code>user.inconsistent</code> if
     *         checked data does not have the save id as requested in the URL.
     */
    @PUT
    @Path("{id}")
    Response createOrUpdateUser(@PathParam("id") Integer id, User user);

}
