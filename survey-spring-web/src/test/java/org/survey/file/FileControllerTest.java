package org.survey.file;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.file.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-dispatcher-servlet-test.xml")
@WebAppConfiguration
public class FileControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private File[] files;

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

        files = (File[]) modelAndView.getModel().get("files");
        Assert.assertNotNull(files);
        Assert.assertEquals(1, files.length);

    }

    @Test
    public void deleteFile() throws Exception {
        fileUpload();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/file/delete/" + files[0].getId().toString());

        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/file/files"));
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();

        files = (File[]) modelAndView.getModel().get("files");
        Assert.assertNull(files);
    }
}
