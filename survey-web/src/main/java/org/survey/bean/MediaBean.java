package org.survey.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;

@Component
@Scope("request")
public class MediaBean {
    private static final String IMAGE_FORMAT_NAME = "png";
    private static final Color FOREGROUND_COLOR = new Color(0, 0, 0);
    private static final int TEXT_LOCATION_X = 20;
    private static final int TEXT_LOCATION_Y = 18;
    private static final Font FONT = new Font("Helvetica Neue", Font.TRUETYPE_FONT, 14);
    private static final int IMAGE_HEIGHT = 25;
    private static final int IMAGE_WIDTH = 150;
    private static final Color BACKGROUND_COLOR = new Color(0xe0, 0xe0, 0xe0);

    public StreamedContent getImage() throws IOException {
        if (isRenderPhase()) {
            // Return a stub StreamedContent when rendering view
            return new DefaultStreamedContent();
        } else {
            // Return a real StreamedContent with the image bytes when not in
            // render phase.
            String email = getRequestParameter("email");
            return getImageAsStreamedContent(email);
        }
    }

    private DefaultStreamedContent getImageAsStreamedContent(String email) throws IOException {
        DefaultStreamedContent streamedContent = null;
        if (!StringUtils.isEmpty(email)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage image = createImage(email);
            ImageIO.write(image, IMAGE_FORMAT_NAME, outputStream);
            streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()), IMAGE_FORMAT_NAME, email);
        }
        return streamedContent;
    }

    // TODO createImage belongs to a service
    private BufferedImage createImage(String text) throws IOException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setBackground(BACKGROUND_COLOR);
        graphics2D.setColor(FOREGROUND_COLOR);
        graphics2D.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        graphics2D.setFont(FONT);
        graphics2D.drawString(text, TEXT_LOCATION_X, TEXT_LOCATION_Y);
        return image;
    }

    protected boolean isRenderPhase() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE;
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }
}
