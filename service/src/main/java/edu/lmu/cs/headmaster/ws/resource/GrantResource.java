package edu.lmu.cs.headmaster.ws.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.lmu.cs.headmaster.ws.domain.Grant;
import edu.lmu.cs.headmaster.ws.types.GrantStatus;
import edu.lmu.cs.headmaster.ws.util.ServiceException;

/**
 * The JAX-RS interface for operating on student resources.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GrantResource {
    /**
     * Possible resource error messages.
     */
    String GRANT_OVERSPECIFIED = "grant.overspecified";
    String GRANT_INCONSISTENT = "grant.inconsistent";
    String GRANT_AWARDED_STATUS_INCORRECT = "grant.awarded.status.incorrect";
    String GRANT_NOT_FOUND = "grant.not.found";
    String GRANT_QUERY_PARAMETERS_MISSING = "grant.query.parameters.missing";

    /**
     * Returns grants according to the search parameters
     *
     * @param query
     *            the query
     * @param skip
     *            the number of initial results to skip
     * @param max
     *            the maximum number of results to display
     *
     * @return the (paginated) set of grants matching the query parameters
     */
    @GET
    List<Grant> getGrants(@QueryParam("q") String query, @QueryParam("awarded") GrantStatus awarded,
            @QueryParam("presented") Boolean grantPresented, @QueryParam("skip") @DefaultValue("0") int skip,
            @QueryParam("max") @DefaultValue("100") int max);

    /**
     * Creates a grant for which the server will generate the id.
     *
     * @param grant
     *            the grant object to create. The grant must have a null id.
     * @return A response with HTTP 201 on success, or a response with HTTP 400 and message
     *         <code>grant.overspecified</code> if the grant's id is not null.
     */
    @POST
    Response createGrant(Grant grant);

    /**
     * Supposed to save the representation of the grant with the given id. Inconsistent data should result in HTTP 400,
     * while a successful PUT should return Response.noContent.
     *
     * @param id
     *            the id of the grant to save.
     * @return A response with HTTP 204 no content on success, or a response with HTTP 400 and message
     *         <code>grant.inconsistent</code> if checked data does not have the save id as requested in the URL.
     */
    @PUT
    @Path("{id}")
    @RolesAllowed({"headmaster", "faculty", "staff"})
    Response createOrUpdateGrant(@PathParam("id") Long id, Grant grant);

    /**
     * Returns the grant with the given id.
     *
     * @param id
     *            the id of the requested grant.
     * @return the grant with the given id.
     * @throws ServiceException
     *             if there is no grant with the given id, causing the framework to generate an HTTP 404.
     */
    @GET
    @Path("{id}")
    Grant getGrantById(@PathParam("id") Long id);
}
