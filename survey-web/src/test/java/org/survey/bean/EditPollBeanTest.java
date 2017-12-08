package org.survey.bean;

import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.ServiceTestConfig;
import org.survey.model.poll.Poll;
import org.survey.model.poll.Question;
import org.survey.model.poll.QuestionType;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

/**
 * Test EditPollBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class EditPollBeanTest {
    EditPollBeanMock editPollBean;
    @Resource
    private PollService pollService;
    @Resource
    private UserService userService;
    private User user;

    @Before
    public void setUp() throws MalformedURLException {
        editPollBean = new EditPollBeanMock();
        editPollBean.setPollService(pollService);
        this.user = userService.create(new User("username", "password", "email", Role.ROLE_USER));
    }

    @After
    public void tearDown() {
        Poll[] polls = pollService.findAll();
        for (Poll poll : polls) {
            pollService.delete(poll.getName());
        }
        userService.delete(this.user.getUsername());
    }

    @Test
    public void addPoll() {
        String result = editPollBean.addPoll();
        Assert.assertEquals(
                "editPollBean.register returned an unexpected value",
                "editPoll", result);
//        Poll createdPoll = new Poll("poll");
//        editPollBean.setPoll(createdPoll);
        editPollBean.getPoll().setName("poll");
        result = editPollBean.savePoll();
        Assert.assertEquals(
                "editPollBean.savePoll returned an unexpected value",
                "pollSaved", result);
        Poll pollFromDatabase = pollService.findOne("poll");
        Assert.assertNotNull("registered poll was not added to database",
                pollFromDatabase);
        Assert.assertEquals("poll", pollFromDatabase.getName());
    }
    
    @Ignore("poll is attached and test does not represent the situation in runtime")
    @Test
    public void addQuestion() {
        addPoll();
        String result = editPollBean.editPoll();
        Assert.assertEquals(
                "editPollBean.editPoll returned an unexpected value",
                "editPoll", result);
        editPollBean.addQuestion();
        Assert.assertNotNull(editPollBean.getPoll().getQuestions());
        Assert.assertEquals(1, editPollBean.getPoll().getQuestions().size());
        Question question = editPollBean.getPoll().getQuestions().get(0);
        question.setText("text1");
        result = editPollBean.savePoll();
        Assert.assertEquals(
                "editPollBean.savePoll returned an unexpected value",
                "pollSaved", result);
        Poll pollFromDatabase = pollService.findOne("poll");
        Assert.assertNotNull("registered poll was not added to database",
                pollFromDatabase);
        Assert.assertEquals(1, pollFromDatabase.getQuestions().size());
        Assert.assertEquals("text1", pollFromDatabase.getQuestions().get(0)
                .getText());
    }
    
    @Ignore("poll is attached and test does not represent the situation in runtime")
    @Test
    public void questionTypeChanged() {
        // addQuestion calls addPoll
        addQuestion();
        editPollBean.getPoll().getQuestions().get(0).setType(QuestionType.BOOLEAN);
        editPollBean.questionTypeChanged(0);
        String result = editPollBean.savePoll();
        Assert.assertEquals(
                "editPollBean.savePoll returned an unexpected value",
                "pollSaved", result);
        Poll pollFromDatabase = pollService.findOne("poll");
        QuestionType type = pollFromDatabase.getQuestions().get(0)
        .getType();
        Assert.assertEquals(QuestionType.BOOLEAN, type);
    }

    @Test
    public void addPollWithError() {
        String result = editPollBean.addPoll();
        Assert.assertEquals(
                "editPollBean.addPoll returned an unexpected value",
                "editPoll", result);
        Poll createdPoll = new Poll("poll");
        editPollBean.setPoll(createdPoll);
        result = editPollBean.savePoll();
        Assert.assertEquals(
                "editPollBean.savePoll returned an unexpected value",
                "pollSaved", result);
        Poll pollFromDatabase = pollService.findOne(createdPoll.getName());
        Assert.assertNotNull("registered poll was not added to database",
                pollFromDatabase);
        Assert.assertEquals("poll", pollFromDatabase.getName());
        result = editPollBean.addPoll();
        createdPoll.setId(null);
        editPollBean.setPoll(createdPoll);
        result = editPollBean.savePoll();
        Assert.assertNull(result);
        Assert.assertEquals("Poll with this name already exists.",
                editPollBean.getMessage());
    }

    private class EditPollBeanMock extends EditPollBean {
        BeanTestHelper beanTestHelper = new BeanTestHelper();
        @Override
        void showMessage(String id, String messageKey, Exception e) {
            beanTestHelper.showMessage(id, messageKey);
        }
        public String getMessage() {
            return beanTestHelper.getMessage();
        }
        @Override
        protected User getCurrentUser() {
            return user;
        }
    }
}
