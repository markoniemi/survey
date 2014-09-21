package org.survey.user.bean;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.poll.model.Poll;
import org.survey.poll.model.Question;
import org.survey.poll.service.PollService;

/**
 * Test EditPollBean. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class EditPollBeanTest {
    EditPollBeanMock editPollBean;
    @Autowired
    private PollService pollService;

    @Before
    public void setUp() throws MalformedURLException {
        editPollBean = new EditPollBeanMock();
        editPollBean.setPollService(pollService);
    }

    @After
    public void tearDown() {
        Poll[] polls = pollService.findAll();
        for (Poll poll : polls) {
            pollService.delete(poll.getName());
        }
    }

    @Test
    public void addPoll() {
        String result = editPollBean.addPoll();
        Assert.assertEquals(
                "editPollBean.register returned an unexpected value",
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
        result = editPollBean.editPoll();
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
        pollFromDatabase = pollService.findOne(createdPoll.getName());
        Assert.assertNotNull("registered poll was not added to database",
                pollFromDatabase);
        Assert.assertEquals(1, pollFromDatabase.getQuestions().size());
        Assert.assertEquals("text1", pollFromDatabase.getQuestions().get(0)
                .getText());
        // TODO move to another test case
//        editPollBean.getPoll().getQuestions().get(0).setType(QuestionType.BOOLEAN.name());
//        editPollBean.questionTypeChanged(0);
//        result = editPollBean.savePoll();
//        Assert.assertEquals(
//                "editPollBean.savePoll returned an unexpected value",
//                "pollSaved", result);
//        pollFromDatabase = pollService.findOne(createdPoll.getName());
//        String type = pollFromDatabase.getQuestions().get(0)
//        .getType();
//        Assert.assertEquals(QuestionType.BOOLEAN.name(), type);
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
        void showMessage(String id, String messageKey) {
            beanTestHelper.showMessage(id, messageKey);
        }
        public String getMessage() {
            return beanTestHelper.getMessage();
        }
    }
}
