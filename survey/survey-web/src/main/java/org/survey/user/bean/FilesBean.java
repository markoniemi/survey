package org.survey.user.bean;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.file.model.File;
import org.survey.file.service.FileService;

@Slf4j
@Component
@Scope("request")
public class FilesBean {
    @Setter
    @Autowired
    private FileService fileService;
    @Getter
    @SuppressWarnings("PMD.UnusedPrivateField")
    private File[] files;

    @PostConstruct
    public void initialize() {
        files = fileService.findAll();
    }

    /**
     * Called by a4j:mediaOutput component.
     * 
     * <pre>
     * &lt;a4j:mediaOutput element="img" cacheable="false" session="false"
     * createContent="#{filesBean.paint}" value="#{file.id}"
     * style="width:50px; height:50px;"
     * mimeType="image/jpeg" /&gt;
     * </pre>
     */
    public void paint(OutputStream outputStream, Object data)
            throws IOException {
        log.debug("paint: {}", data);
        Long id = (Long) data;
        if (id != null) {
            File file = fileService.findOne(id);
            outputStream.write(file.getContent());
        }
    }
}
