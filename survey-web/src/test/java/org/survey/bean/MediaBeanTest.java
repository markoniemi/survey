package org.survey.bean;

import java.io.IOException;

import lombok.experimental.Delegate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.StreamedContent;

/**
 * Test MediaBean. MediaBean does not need spring config.
 */
public class MediaBeanTest {
	private MediaBeanMock mediaBean;

	@Before
	public void setUp() {
	    mediaBean = new MediaBeanMock();
	}

	@Test
	public void getImage() throws IOException {
	    mediaBean.setRequestParameter("email");
		StreamedContent streamedContent = mediaBean.getImage();
		Assert.assertNotNull(streamedContent);
		Assert.assertNotNull(streamedContent.getStream());
		Assert.assertEquals("png", streamedContent.getContentType());
		Assert.assertEquals("email", streamedContent.getName());
	}
	@Test
	public void getImageInRenderPhase() throws IOException {
	    mediaBean.setRequestParameter("email");
	    mediaBean.setRenderPhase(true);
	    StreamedContent streamedContent = mediaBean.getImage();
	    Assert.assertNotNull(streamedContent);
	    Assert.assertNull(streamedContent.getStream());
	}

	@Test
	public void paintWithNullData() throws IOException {
	    mediaBean.setRequestParameter(null);
	    StreamedContent streamedContent = mediaBean.getImage();
		Assert.assertNull(streamedContent);
	}
	
	
	public class MediaBeanMock extends MediaBean {
	    @Delegate
	    BeanTestHelper beanTestHelper = new BeanTestHelper();

        @Override
        protected String getRequestParameter(String parameterName) {
            return beanTestHelper.getRequestParameter(parameterName);
        }
	}
}
