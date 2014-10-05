package org.survey.user.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

import org.hsqldb.lib.StringUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@Slf4j
public class MediaBean {
    private static final String IMAGE_FORMAT_NAME = "png";
    private static final Color FOREGROUND_COLOR = new Color(0, 0, 0);
    private static final int TEXT_LOCATION_X = 20;
    private static final int TEXT_LOCATION_Y = 18;
    private static final Font FONT = new Font("Serif", Font.TRUETYPE_FONT, 12);
    private static final int IMAGE_HEIGHT = 25;
    private static final int IMAGE_WIDTH = 150;
    private static final Color BACKGROUND_COLOR = new Color(0xcd, 0xeb, 0x8e);

    // TODO refactor
    public StreamedContent getImage()
            throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String email = context.getExternalContext().getRequestParameterMap().get("email");
            DefaultStreamedContent streamedContent = null;
            if (!StringUtil.isEmpty(email)) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BufferedImage image = createImage(email);
                ImageIO.write(image, IMAGE_FORMAT_NAME, outputStream);
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(outputStream.toByteArray()));
            }
            return streamedContent;
        }
    }
    /**
     * Called by a4j:mediaOutput component.
     * 
     * <pre>
     * &lt;a4j:mediaOutput element="img" cacheable="false" session="false"
     * createContent="#{mediaBean.paint}" value="#{user.email}"
     * mimeType="image/jpeg" /&gt;
     * </pre>
     */
    // TODO no longer needed
    public void paint(OutputStream outputStream, Object data)
            throws IOException {
        log.debug("paint: {}", data);
        String text = (String) data;
        if (!StringUtil.isEmpty(text)) {
            BufferedImage image = createImage(text);
            ImageIO.write(image, IMAGE_FORMAT_NAME, outputStream);
        }
    }

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
}
