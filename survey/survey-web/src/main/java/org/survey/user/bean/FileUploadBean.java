package org.survey.user.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.file.model.File;
import org.survey.file.service.FileService;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

/**
 * FileUpload requires two context-parameter's to be set in web.xml:
 * 
 * createTempFiles boolean attribute which defines whether the uploaded files
 * are stored in temporary files or available in the listener directly as byte[]
 * data (false for this example). maxRequestSize attribute defines max size in
 * bytes of the uploaded files (1000000 for this example).
 * 
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
    private transient UserBean userBean;

    /**
     * Called when user presses Upload button in file upload dialog. Saves file
     * to FileService.
     */
    public void uploadFile(FileUploadEvent event) {
        File file = createFile(event.getUploadedFile());
        log.debug(file.toString());
        fileService.create(file);
    }

    private User getUser() {
        return userService.findOne(userBean.getUsername());
    }

    /**
     * Create a File from UploadedFile.
     */
    private File createFile(UploadedFile uploadedFile) {
        File file = new File();
        file.setFilename(uploadedFile.getName());
        file.setMimeType(uploadedFile.getContentType());
        file.setContent(uploadedFile.getData());
        file.setOwner(getUser());
        // file.setOwner(userBean.getUsername());
        file.setCreateTime(System.currentTimeMillis());
        file.setSize(uploadedFile.getSize());
        return file;
    }
}