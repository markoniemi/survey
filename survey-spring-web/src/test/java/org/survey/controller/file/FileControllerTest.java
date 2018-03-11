package org.survey.controller.file;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.survey.config.WebMvcConfig;
import org.survey.model.file.File;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/FileControllerTest.xml")
@WebAppConfiguration
public class FileControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * {@link http://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request}
     */
    @Test
    public void fileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.fileUpload("/file/save").file(file);

        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/file/files"));
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();

        request = MockMvcRequestBuilders.get("/file/files");
        resultActions = mockMvc.perform(request);
        modelAndView = resultActions.andReturn().getModelAndView();

        File[] files = (File[]) modelAndView.getModel().get("files");
        Assert.assertNotNull(files);
        Assert.assertEquals(2, files.length);
    }

    @Test
    public void deleteFile() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/file/delete/" + 1);

        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/file/files"));
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();

        File[] files = (File[]) modelAndView.getModel().get("files");
        Assert.assertNull(files);
    }

    @Test
    public void downloadFile() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/file/" + 1);

        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        Assert.assertEquals("application/octet-stream", response.getContentType());
        // TODO assert file
    }

    @Test
    public void newFile() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/file/new");

        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
         resultActions.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/file/file.jsp"));
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
        File file = (File) modelAndView.getModel().get("file");
        Assert.assertNotNull(file);
    }
}
