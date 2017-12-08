package org.survey.bean;

import java.io.IOException;

import javax.annotation.Resource;

import lombok.experimental.Delegate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.StreamedContent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.model.file.File;
import org.survey.repository.file.FileRepository;
import org.survey.service.file.FileService;

/**
 * Test FilesBean. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class FilesBeanTest {
//	@Resource
	FilesBeanMock filesBean;
	@Resource
	FileRepository fileRepository;
	@Resource
	private FileService fileService;
	private File savedFile;

	@Before
	public void setUp() {
	    filesBean = new FilesBeanMock();
	    filesBean.setFileService(fileService);
		savedFile = fileRepository.save(new File("filename", "mimeType",
				"content".getBytes()));
	}

	@After
	public void tearDown() {
		fileRepository.deleteAll();
	}

	@Test
	public void initialize() throws IOException {
	    filesBean.initialize();
	    Assert.assertNotNull(filesBean.getFiles());
	}
	
    @Test
    public void getImage() throws IOException {
        filesBean.setRequestParameter(savedFile.getId().toString());
        StreamedContent streamedContent = filesBean.getImage();
        Assert.assertNotNull(streamedContent);
        Assert.assertNotNull(streamedContent.getStream());
    }
    @Test
    public void getImageInRenderPhase() throws IOException {
        filesBean.setRequestParameter(savedFile.getId().toString());
        filesBean.setRenderPhase(true);
        StreamedContent streamedContent = filesBean.getImage();
        Assert.assertNotNull(streamedContent);
        Assert.assertNull(streamedContent.getStream());
    }

    @Test
    public void paintWithNullData() throws IOException {
        filesBean.setRequestParameter(null);
        StreamedContent streamedContent = filesBean.getImage();
        Assert.assertNull(streamedContent);
    }
    public class FilesBeanMock extends FilesBean {
        @Delegate
        BeanTestHelper beanTestHelper = new BeanTestHelper();

        @Override
        protected String getRequestParameter(String parameterName) {
            return beanTestHelper.getRequestParameter(parameterName);
        }
    }
}
