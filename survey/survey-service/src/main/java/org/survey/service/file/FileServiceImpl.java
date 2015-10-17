package org.survey.service.file;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import lombok.extern.log4j.Log4j2;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.survey.model.file.File;
import org.survey.repository.file.FileRepository;

import com.google.common.collect.Iterables;

@WebService(endpointInterface = "org.survey.service.file.FileService", serviceName = "fileService")
@Log4j2
public class FileServiceImpl implements FileService {
    @Context
    HttpServletRequest request;
    @Resource
    FileRepository fileRepository;
    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    @Override
    public File[] findAll() {
        Iterable<File> files = fileRepository.findAll();
        // return empty list instead of null
        if (Iterables.isEmpty(files)) {
            return EMPTY_FILE_ARRAY;
        } else {
            return Iterables.toArray(files, File.class);
        }
    }

    @Override
    public File create(@WebParam(name = "file") File file) {
        log.debug(file);
        return fileRepository.save(file);
    }

    @Override
    public File update(@WebParam(name = "file") File file) {
        return fileRepository.save(file);
    }

    @Override
    // @GET
    // @Path("/downloadFile")
    // @Produces("application/pdf")
    public Response downloadFile() {
        java.io.File file = new java.io.File("test.pdf");
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=test.pdf");
        return response.build();
    }

    @Override
    // @POST
    // @Path("/uploadFile")
    // @Consumes(MediaType.MULTIPART_FORM_DATA)
    // public Response uploadFile(List<Attachment> attachments, @Context
    // HttpServletRequest request) {
    public void uploadFile(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            DataHandler handler = attachment.getDataHandler();
            try {
                InputStream stream = handler.getInputStream();
                MultivaluedMap<String, String> map = attachment.getHeaders();
                System.out.println("fileName Here" + getFileName(map));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                IOUtils.copy(stream, out);
                File file = createFile(getFileName(map), "mimeType", "owner", out.toByteArray());
//                OutputStream out = new FileOutputStream(new java.io.File("C:/uploads/" + getFileName(map)));
//
//                int read = 0;
//                byte[] bytes = new byte[1024];
//                while ((read = stream.read(bytes)) != -1) {
//                    out.write(bytes, 0, read);
//                }
//                stream.close();
//                out.flush();
//                out.close();
                fileRepository.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        return Response.ok("file uploaded").build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "unknown";
    }
    private File createFile(String filename, String mimeType, String owner, byte[] fileContent) {
        File file = new File();
        file.setFilename(filename);
        file.setMimeType(mimeType);
        file.setContent(fileContent);
//        file.setOwner(owner);
        file.setCreateTime(System.currentTimeMillis());
        file.setSize(Long.valueOf(fileContent.length));
        // TODO change files rest to files/:user/:filename
        file.setUrl("/survery-web/api/rest/files/");
        return file;
    }
    @Override
    public File findOne(@WebParam(name = "id") long id) {
        return fileRepository.findOne(id);
    }

    @Override
    public boolean exists(@WebParam(name = "id") long id) {
        return fileRepository.exists(id);
    }

    @Override
    public void delete(@WebParam(name = "id") long id) {
        if (fileRepository.exists(id)) {
            fileRepository.delete(id);
        }
    }

    @Override
    public long count() {
        return fileRepository.count();
    }
}
