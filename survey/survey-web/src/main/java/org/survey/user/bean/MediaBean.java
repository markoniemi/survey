package org.survey.user.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

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

    /**
     * Called by a4j:mediaOutput component.
     * 
     * <pre>
     * &lt;a4j:mediaOutput element="img" cacheable="false" session="false"
     * createContent="#{mediaBean.paint}" value="#{user.email}"
     * mimeType="image/jpeg" /&gt;
     * </pre>
     */
    public void paint(OutputStream outputStream, Object data)
            throws IOException {
        log.debug("paint: {}", data);
        String text = (String) data;
        if (text != null) {
            BufferedImage img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = img.createGraphics();
            graphics2D.setBackground(BACKGROUND_COLOR);
            graphics2D.setColor(FOREGROUND_COLOR);
            graphics2D.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
            graphics2D.setFont(FONT);
            graphics2D.drawString(text, TEXT_LOCATION_X, TEXT_LOCATION_Y);
            ImageIO.write(img, IMAGE_FORMAT_NAME, outputStream);
        }
    }
}
