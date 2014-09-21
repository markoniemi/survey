package org.survey.user.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.file.model.File;
import org.survey.file.repository.FileRepository;
import org.survey.user.bean.FilesBean;

/**
 * Test FilesBean. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class FilesBeanTest {
	@Autowired
	FilesBean filesBean;
	@Autowired
	FileRepository fileRepository;
	private File savedFile;

	@Before
	public void setUp() {
		savedFile = fileRepository.save(new File("filename", "mimeType",
				"content".getBytes()));
	}

	@After
	public void tearDown() {
		fileRepository.deleteAll();
	}

	@Test
	public void paint() throws IOException {
		OutputStream outputStream = new ByteArrayOutputStream();
		filesBean.paint(outputStream, null);
		Assert.assertEquals("", outputStream.toString());
		filesBean.paint(outputStream, savedFile.getId());
		Assert.assertEquals("content", outputStream.toString());
	}
}
