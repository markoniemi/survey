package org.survey.user.bean;

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
import org.survey.file.model.File;
import org.survey.file.service.FileService;
import org.survey.user.FacesUtil;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

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

    /**
     * Called when user presses Upload button in file upload dialog. Saves file
     * to FileService.
     */
    public void upload() {
        try {
            byte[] fileContent = getFileContent();
            File createdFile = createFile(file, fileContent);
            fileService.create(createdFile);
        } catch (IOException e) {
            showMessage(null, "fileUploadError");
        }
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
    void showMessage(String id, String messageKey) {
        FacesUtil.showMessage(id, messageKey);
    }
}