package org.survey.service.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.survey.model.file.File;
import org.survey.repository.file.FileRepository;

import com.google.common.collect.Iterables;

import lombok.extern.slf4j.Slf4j;

@WebService(endpointInterface = "org.survey.service.file.FileService", serviceName = "fileService")
@Slf4j
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
        log.debug(file.getFilename());
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
    public Response downloadFile(Long id) {
        File file = fileRepository.findOne(id);
        // java.io.File file = new java.io.File("test.pdf");
        ResponseBuilder response = Response.ok((Object) file, file.getMimeType());
        response.header("Content-Disposition", "attachment; filename=" + file.getFilename());
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
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                MultivaluedMap<String, String> map = attachment.getHeaders();
                String fileName = getFileName(map);
                log.debug("uploadFile: {}", fileName);
                if (StringUtils.isNotEmpty(fileName)) {
                    DataHandler handler = attachment.getDataHandler();
                    InputStream inputStream = handler.getInputStream();
                    IOUtils.copy(inputStream, outputStream);
                    File file = createFile(fileName, "mimeType", "owner", outputStream.toByteArray());
                    // TODO move to finally block
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                    // TODO for some reason there is an extra file with 0 size
                    if (file.getContent().length > 0) {
                        fileRepository.save(file);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // return Response.ok("file uploaded").build();
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
        return null;
    }

    private File createFile(String filename, String mimeType, String owner, byte[] fileContent) {
        File file = new File();
        file.setFilename(filename);
        file.setMimeType(mimeType);
        file.setContent(fileContent);
        // file.setOwner(owner);
        file.setCreateTime(System.currentTimeMillis());
        // TODO change files rest to files/:user/:filename
        file.setUrl("/survey-web/api/rest/files/");
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
