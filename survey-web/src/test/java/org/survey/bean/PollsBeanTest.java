package org.survey.bean;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ManagedBeanTestConfig;
import org.survey.model.poll.Poll;
import org.survey.service.poll.PollService;

/**
 * Test PollsBean. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ManagedBeanTestConfig.class))
public class PollsBeanTest {
	PollsBean pollsBean;
	@Resource
	PollService pollService;

	@Before
	public void setUp() {
		pollService
				.create(new Poll("poll"));
		pollsBean = new PollsBeanMock();
		pollsBean.setPollService(pollService);
		pollsBean.initialize();
	}

	@After
	public void tearDown() {
		for (Poll poll : pollService.findAll()) {
            pollService.delete(poll.getName());
        }
	}

	@Test
	public void getPolls() {
	    List<Poll> polls = pollsBean.getPolls();
		Assert.assertEquals(1, polls.size());
		pollsBean.getPolls();
		Assert.assertEquals(1, polls.size());
	}

	@Test
	public void getPollsWithUpdate() {
	    List<Poll> polls = pollsBean.getPolls();
		Assert.assertEquals(1, polls.size());
		Poll createdPoll = new Poll("poll2");
		pollsBean.getPollService().create(createdPoll);
		Assert.assertEquals(2, pollsBean.getPollService().findAll().length);
		// fake a page transition
		pollsBean = new PollsBeanMock();
		pollsBean.setPollService(pollService);
		pollsBean.initialize();
		polls = pollsBean.getPolls();
		Assert.assertEquals(2, polls.size());
	}

	@Test
	public void deletePoll() throws Exception {
		Poll createdPoll = new Poll("poll2");
		pollsBean.getPollService().create(createdPoll);
		((PollsBeanMock)pollsBean).setRequestParameter("poll2");
		pollsBean.deletePoll();
		Assert.assertEquals(1, pollsBean.getPollService().count());
	}

	private class PollsBeanMock extends PollsBean {
        BeanTestHelper beanTestHelper = new BeanTestHelper();

        public void setRequestParameter(String requestParameter) {
            beanTestHelper.setRequestParameter(requestParameter);
        }

        @Override
        protected String getRequestParameter(String parameterName) {
            return beanTestHelper.getRequestParameter();
        }
	}
}
