package org.survey.user.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.file.model.File;
import org.survey.file.repository.FileRepository;
import org.survey.file.service.FileService;
import org.survey.user.model.Role;
import org.survey.user.model.User;
import org.survey.user.repository.UserRepository;

/**
 * Test FileUploadBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class FileUploadBeanTest {
    private FileUploadBeanMock fileUploadBean;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;

    @Before
    public void setUp() {
        fileUploadBean = new FileUploadBeanMock();
        User user = userRepository.save(new User("username", "", "", Role.ROLE_USER));
        fileUploadBean.setUser(user);
        fileUploadBean.setFileService(fileService);
    }

    @After
    public void tearDown() {
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void upload() throws Exception {
        PartMock uploadedFile = new PartMock("filename", "mimeType", "content".getBytes());
        fileUploadBean.setFile(uploadedFile);
        fileUploadBean.upload();
        Iterable<File> files = fileRepository.findAllByFilename("filename");
        Assert.assertEquals(1, IteratorUtils.toList(files.iterator()).size());
    }
    
    public class UserBeanMock extends UserBean {
        private User user;

        public UserBeanMock(User user) {
            this.user = user;
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }
    }

    @SuppressWarnings("serial")
    private class FileUploadBeanMock extends FileUploadBean {
        @Getter
        @Setter
        User user;
        @Delegate
        BeanTestHelper beanTestHelper = new BeanTestHelper();

        @Override
        void showMessage(String id, String messageKey) {
            beanTestHelper.showMessage(id, messageKey);
        }
    }

    @Data
    @RequiredArgsConstructor
    private class PartMock implements Part {
        @NonNull
        private String name;
        @NonNull
        private String contentType;
        private long size;
        @NonNull
        private byte[] data;

        @Override
        public void delete() throws IOException {
        }

        @Override
        public String getHeader(String headerName) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String headerName) {
            return null;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public void write(String fileName) throws IOException {
        }
    }
}
