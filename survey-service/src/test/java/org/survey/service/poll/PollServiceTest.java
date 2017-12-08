package org.survey.service.poll;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.survey.ServiceConfig;

@ContextHierarchy(@ContextConfiguration(classes = ServiceConfig.class))
public class PollServiceTest extends PollServiceTestBase {
}
