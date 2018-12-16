package org.survey.service.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.survey.model.file.File;
import org.survey.repository.file.FileRepository;

import com.google.common.collect.Iterables;

import lombok.extern.log4j.Log4j2;

@WebService(endpointInterface = "org.survey.service.file.FileService", serviceName = "fileService")
@Log4j2
public class FileServiceImpl implements FileService {
    @Context
    HttpServletRequest request;
    @Resource
    FileRepository fileRepository;

    @Override
    public File[] findAll() {
        return Iterables.toArray(fileRepository.findAll(), File.class);
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
    public Response downloadFile(Long id) {
        File file = fileRepository.findById(id).orElse(null);
        if (file == null) {
            return Response.noContent().build();
        }
        ResponseBuilder response = Response.ok((Object) file, file.getMimeType());
        response.header("Content-Disposition", "attachment; filename=" + file.getFilename());
        return response.build();
    }

    @Override
    public void uploadFile(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            uploadFile(attachment);
        }
    }

    @Override
    public File findById(@WebParam(name = "id") long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(@WebParam(name = "id") long id) {
        return fileRepository.existsById(id);
    }

    @Override
    public void delete(@WebParam(name = "id") long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        }
    }

    @Override
    public long count() {
        return fileRepository.count();
    }

    private Long uploadFile(Attachment attachment) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            String fileName = getFileName(attachment.getHeaders());
            log.debug("uploadFile: {}", fileName);
            if (StringUtils.isNotEmpty(fileName)) {
                inputStream = attachment.getDataHandler().getInputStream();
                outputStream = new ByteArrayOutputStream();
                IOUtils.copy(inputStream, outputStream);
                File file = createFile(fileName, "mimeType", "owner", outputStream.toByteArray());
                outputStream.flush();
                // for some reason there is an extra file with 0 size
                if (file.getContent().length > 0) {
                    return fileRepository.save(file).getId();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return null;
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
}
