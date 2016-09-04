package org.survey.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import org.junit.Assert;
import org.junit.Test;

public class VersionBeanTest {
	private VersionBean versionBean;

	@Test
	public void getVersion() {
		versionBean = new VersionBeanMock();
		versionBean.initialize();
		Assert.assertEquals("1.1-SNAPSHOT", versionBean.getVersion());
		Assert.assertEquals("Maven Integration for Eclipse",
				versionBean.getCreatedBy());
		Assert.assertEquals("Marko Niemi", versionBean.getBuiltBy());
	}

	private class VersionBeanMock extends VersionBean {
		@Override
		Manifest getManifest() throws IOException {
			InputStream inputStream = new FileInputStream(
					"src/test/resources/META-INF/MANIFEST.MF");
			return new Manifest(inputStream);
		}
	}
}
