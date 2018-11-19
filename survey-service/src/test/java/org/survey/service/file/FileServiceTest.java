package org.survey.service.file;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.survey.config.ServiceConfig;
import org.survey.model.file.File;

@ContextHierarchy(@ContextConfiguration(classes = ServiceConfig.class))
public class FileServiceTest extends FileServiceTestBase {
    @Test
    public void downloadFile() {
        create();
        Response response = entityService.downloadFile(savedEntities.get(0).getId());
        MultivaluedMap<String, Object> metadata = response.getMetadata();
        Assert.assertEquals("text/plain; charset=utf-8", metadata.getFirst("Content-Type"));
        Assert.assertEquals("attachment; filename=filename0", metadata.getFirst("Content-Disposition"));
        assertEntity(savedEntities.get(0), (File) response.getEntity());
    }

    @Test
    public void uploadFile() {
        List<Attachment> attachments = createAttachments();
        entityService.uploadFile(attachments);
        File[] files = entityService.findAll();
        Assert.assertEquals(1, files.length);
        Assert.assertEquals("uploadedFile", files[0].getFilename());
    }

    private List<Attachment> createAttachments() {
        List<Attachment> attachments = new ArrayList<>();
        ByteArrayInputStream contents = new ByteArrayInputStream("content".getBytes());
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        String[] cd = { "attachment; filename=uploadedFile" };
        headers.put("Content-Disposition", Arrays.asList(cd));
        Attachment attachment = new Attachment(contents, headers);
        attachments.add(attachment);
        return attachments;
    }
}
