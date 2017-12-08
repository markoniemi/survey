package org.survey.service.file;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.survey.ServiceConfig;

@ContextHierarchy(@ContextConfiguration(classes = ServiceConfig.class))
public class FileServiceTest extends FileServiceTestBase {
}
