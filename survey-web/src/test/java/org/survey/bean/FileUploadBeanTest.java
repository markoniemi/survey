package org.survey.bean;

import lombok.*;
import lombok.experimental.Delegate;
import org.apache.commons.collections.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.model.file.File;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.file.FileRepository;
import org.survey.repository.user.UserRepository;
import org.survey.service.file.FileService;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Test FileUploadBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class FileUploadBeanTest {
    private FileUploadBeanMock fileUploadBean;
    @Resource
    private FileRepository fileRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
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
        void showMessage(String id, String messageKey, Exception e) {
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
            return "filename=\"" + name + "\"";
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

        @Override
        public String getSubmittedFileName() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
