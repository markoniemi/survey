package org.survey.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.file.File;
import org.survey.model.user.User;
import org.survey.service.file.FileService;
import org.survey.service.user.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * FileUpload uses JSF 2.2 h:inputFile component.
 */
@Slf4j
@Component
@Scope("request")
@SuppressWarnings("PMD.UnusedPrivateField")
public class FileUploadBean {
    @Getter
    private String acceptedTypes = "jpg, gif, png, bmp";
    @Setter
    @Resource
    private transient FileService fileService;
    @Setter
    @Resource
    private transient UserService userService;
    @Setter
    @Resource
    transient UserBean userBean;
    @Getter
    @Setter
    private Part file;

    public String addFile() {
        return "editFile";
    }
    protected String getFilename(Part file) {
        String disposition = file.getHeader("content-disposition");
        log.debug(disposition);
        return disposition.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1");
    }
    /**
     * Called when user presses Upload button in file upload dialog. Saves file
     * to FileService.
     */
    public String upload() {
        try {
            byte[] fileContent = getFileContent();
            File createdFile = createFile(file, getFilename(file), fileContent);
            fileService.create(createdFile);
        } catch (IOException e) {
            showMessage(null, "fileUploadError", e);
        }
        return "fileSaved";
    }

    /**
     * Get file as byte array from Part.
     */
    private byte[] getFileContent() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(file.getInputStream(), outputStream);
        return outputStream.toByteArray();
    }

    protected User getUser() {
        return userService.findOne(userBean.getUsername());
    }

    /**
     * Create a File from Part.
     */
    private File createFile(Part uploadedFile, String filename, byte[] fileContent) {
        File file = new File();
        file.setFilename(filename);
        file.setMimeType(uploadedFile.getContentType());
        file.setContent(fileContent);
        file.setOwner(getUser());
        file.setCreateTime(System.currentTimeMillis());
        // TODO change files rest to files/:user/:filename
        file.setUrl("/survery-web/api/rest/files/");
        return file;
    }

    /**
     * showMessage is package private to enable overriding in a test case.
     */
    void showMessage(String id, String messageKey, Exception e) {
        log.error(messageKey, e);
        FacesUtil.showMessage(id, messageKey);
    }
}