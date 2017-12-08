package org.survey.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ServiceRestTestConfig;
import org.survey.service.poll.PollServiceTestBase;

import javax.ws.rs.InternalServerErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-config-rest-service-test.xml", inheritLocations=false)
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
