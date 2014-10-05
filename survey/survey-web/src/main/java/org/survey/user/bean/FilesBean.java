package org.survey.user.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private List<File> files;

    @PostConstruct
    public void initialize() {
        files = Arrays.asList(fileService.findAll());
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
    
    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            File file = fileService.findOne(Long.valueOf(id));
//            Image image = service.find(Long.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(file.getContent()));
        }
    }    
}
