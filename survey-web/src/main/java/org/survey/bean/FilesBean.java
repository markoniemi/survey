package org.survey.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.file.File;
import org.survey.service.file.FileService;

@Component
@Scope("request")
public class FilesBean {
    @Setter
    @Resource
    private FileService fileService;
    @Getter
    @SuppressWarnings("PMD.UnusedPrivateField")
    private List<File> files;

    @PostConstruct
    public void initialize() {
        File[] files = fileService.findAll();
        if (files != null) {
            this.files = Arrays.asList(files);
        }
    }

    public StreamedContent getImage() throws IOException {
        DefaultStreamedContent streamedContent = null;
        if (isRenderPhase()) {
            // Return a stub StreamedContent when rendering view
            return new DefaultStreamedContent();
        } else {
            // Return a real StreamedContent with the image bytes when not in
            // render phase.
            String id = getRequestParameter("id");
            if (!StringUtils.isEmpty(id)) {
                File file = fileService.findOne(Long.valueOf(id));
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(file.getContent()));
            }
        }
        return streamedContent;
    }
    protected boolean isRenderPhase() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE;
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }
}
