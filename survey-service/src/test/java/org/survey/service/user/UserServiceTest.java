package org.survey.service.user;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.survey.ServiceConfig;

@ContextHierarchy(@ContextConfiguration(classes = ServiceConfig.class))
public class UserServiceTest extends UserServiceTestBase {
}
