package org.survey.user.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.user.bean.MediaBean;

/**
 * Test MediaBean. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class MediaBeanTest {
	@Autowired
	private MediaBean mediaBean;
	private ByteArrayOutputStream stream;
	private Object data;

	@Before
	public void setUp() {
		stream = new ByteArrayOutputStream();
		data = new String("test");
	}

	@Test
	public void paint() throws IOException {
		mediaBean.paint(stream, data);
		Assert.assertNotNull(stream);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(
				stream.toByteArray()));
		Assert.assertNotNull(bufferedImage);
		Assert.assertNotNull(bufferedImage.getData());
		Assert.assertNotNull(bufferedImage.getRaster());
	}

	@Test
	public void paintWithNullData() throws IOException {
		mediaBean.paint(stream, null);
		Assert.assertNotNull(stream);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(
				stream.toByteArray()));
		Assert.assertNull(bufferedImage);
	}
}
