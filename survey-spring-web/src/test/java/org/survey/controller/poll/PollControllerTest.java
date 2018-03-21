package org.survey.controller.poll;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.survey.model.poll.Poll;
import org.survey.model.poll.QuestionType;
import org.survey.repository.poll.PollRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@WebAppConfiguration
public class PollControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private PollRepository pollRepository;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void newPoll() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/poll/new");
        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/poll/poll.jsp"));
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
        Poll poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll);
        // fill poll form
        poll.setName("name");
        request = MockMvcRequestBuilders.post("/poll/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("poll", poll);
        resultActions = mockMvc.perform(request);
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/poll/polls"));
        Poll savedPoll = pollRepository.findByName("name");
//    }
//    @Test
//    public void editUser() throws Exception {
        request = MockMvcRequestBuilders.get("/poll/name");
        resultActions = mockMvc.perform(request);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/poll/poll.jsp"));
        modelAndView = resultActions.andReturn().getModelAndView();
        poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll);
        
        // add question
        request = MockMvcRequestBuilders.post("/poll/addQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("poll", poll);
        resultActions = mockMvc.perform(request);
        modelAndView = resultActions.andReturn().getModelAndView();
        poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll.getQuestions());
        Assert.assertEquals(1, poll.getQuestions().size());
        poll.getQuestions().get(0).setType(QuestionType.BOOLEAN);

        // add question
        request = MockMvcRequestBuilders.post("/poll/addQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("poll", poll);
        resultActions = mockMvc.perform(request);
        modelAndView = resultActions.andReturn().getModelAndView();
        poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll.getQuestions());
        Assert.assertEquals(2, poll.getQuestions().size());
        poll.getQuestions().get(1).setType(QuestionType.TEXT);

        // add question
        request = MockMvcRequestBuilders.post("/poll/addQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("poll", poll);
        resultActions = mockMvc.perform(request);
        modelAndView = resultActions.andReturn().getModelAndView();
        poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll.getQuestions());
        Assert.assertEquals(3, poll.getQuestions().size());
        poll.getQuestions().get(2).setType(QuestionType.TEXT);
        
        // save poll
        request = MockMvcRequestBuilders.post("/poll/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("poll", poll);
        resultActions = mockMvc.perform(request);
        resultActions.andExpect(MockMvcResultMatchers.redirectedUrl("/poll/polls"));
        savedPoll = pollRepository.findByName("name");
        Assert.assertNotNull(savedPoll.getQuestions());
        Assert.assertEquals(3, savedPoll.getQuestions().size());

        // edit poll
        request = MockMvcRequestBuilders.get("/poll/name");
        resultActions = mockMvc.perform(request);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/poll/poll.jsp"));
        modelAndView = resultActions.andReturn().getModelAndView();
        poll = (Poll) modelAndView.getModel().get("poll");
        Assert.assertNotNull(poll);
        Assert.assertEquals(3, savedPoll.getQuestions().size());
    }
}
