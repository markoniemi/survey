package org.survey.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.service.poll.PollServiceImplTest;

/**
 * Test PersonManagement using WebService. Spring injects userService with
 * WebService. Must use SpringJUnit4ClassRunner to enable spring injection.
 * Loaded Spring configuration is defined by ContextConfiguration.
 */
//@Ignore("re-write the service tests so that they do not use repositories")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-service-test.xml", inheritLocations=false)
public class PollServiceIT extends PollServiceImplTest {
    /**
     * Override method because expected exception is wrapped in
     * SOAPFaultException in WebService.
     */
    @Test
    public void existsWithNull() {
        super.existsWithNull();
    }
}
