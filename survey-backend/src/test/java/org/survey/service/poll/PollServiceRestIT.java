package org.survey.service.poll;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ServiceRestTestConfig;

import javax.ws.rs.InternalServerErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ServiceRestTestConfig.class))
public class PollServiceRestIT extends PollServiceTestBase {
    /**
     * Override method because expected exception is wrapped in
     * SOAPFaultException in WebService.
     */
    @Test
    @Override
    public void existsWithNull() {
        super.existsWithNull();
    }

    /**
     * Override method because expected exception is wrapped in
     * InternalServerErrorException in Rest.
     */
    @Test
    @Override
    public void createWithError() {
        try {
            create();
            entityService.create(orginalEntities.get(0));
            // TODO add Assert.fail to all occurences of createWithError
            Assert.fail();
        } catch (InternalServerErrorException e) {
        }
    }
}
