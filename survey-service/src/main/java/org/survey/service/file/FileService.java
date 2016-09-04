package org.survey.service.file;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.survey.model.file.File;

@WebService
@Produces({MediaType.APPLICATION_JSON})
@Path("/files")
public interface FileService {
    /**
     * Returns all users from repository.
     */
    @GET
    File[] findAll();

    /**
     * Creates a file to repository.
     * 
     * @throws NullPointerException
     *             if file is null.
     */
    @POST
    File create(@WebParam(name = "file") File file);

    /**
     * Updates a file in repository.
     * 
     * @throws NullPointerException
     *             if file is null.
     * @throws IllegalArgumentException
     *             if file is null.
     */
    @PUT
    File update(@WebParam(name = "file") File file);
    
    /**
     * @see http://www.javatips.net/blog/2013/05/cxf-rest-file-upload?page=2
     */
    @GET
    @Path("/downloadFile")
//    @Produces("application/pdf")
    public Response downloadFile(Long id);
    
    /**
     * @see http://www.javatips.net/blog/2013/05/cxf-rest-file-upload?page=2
     */
    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(List<Attachment> attachments);     
     /**
     * Returns a file by id.
     */
    @GET
    @Path("/{id}")
    File findOne(@PathParam("id") @WebParam(name = "id") long id);

    /**
     * Returns true if a file by id exists.
     */
    @GET
    @Path("/exists/{id}")
    boolean exists(@PathParam("id") @WebParam(name = "id") long id);

    /**
     * Deletes a file by id.
     */
    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") @WebParam(name = "id") long id);

    /**
     * Returns the count of users in repository.
     */
    @GET
    @Path("/count")
    long count();
}
