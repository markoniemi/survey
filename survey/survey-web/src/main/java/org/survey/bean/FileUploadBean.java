package org.survey.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.Part;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.file.File;
import org.survey.model.poll.Poll;
import org.survey.model.user.User;
import org.survey.service.file.FileService;
import org.survey.service.user.UserService;

/**
 * FileUpload uses JSF 2.2 h:inputFile component.
 */
@Slf4j
@Component
@Scope("request")
@SuppressWarnings("PMD.UnusedPrivateField")
public class FileUploadBean implements Serializable {
    private static final long serialVersionUID = -6189250297119147559L;
    @Getter
    private String acceptedTypes = "jpg, gif, png, bmp";
    @Setter
    @Autowired
    private transient FileService fileService;
    @Setter
    @Autowired
    private transient UserService userService;
    @Setter
    @Autowired
    transient UserBean userBean;
    @Getter
    @Setter
    private Part file;
    @Getter
    @Setter
    private String filename;

    public String addFile() {
        return "editFile";
    }
    /**
     * Called when user presses Upload button in file upload dialog. Saves file
     * to FileService.
     */
    public String upload() {
        try {
            byte[] fileContent = getFileContent();
            File createdFile = createFile(file, fileContent);
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
    private File createFile(Part uploadedFile, byte[] fileContent) {
        File file = new File();
        file.setFilename(uploadedFile.getName());
        file.setMimeType(uploadedFile.getContentType());
        file.setContent(fileContent);
        file.setOwner(getUser());
        file.setCreateTime(System.currentTimeMillis());
        file.setSize(uploadedFile.getSize());
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