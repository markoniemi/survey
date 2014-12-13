package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.file.service.FileServiceImplTest;
import org.survey.poll.service.PollServiceImplTest;
import org.survey.user.service.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceImplTest.class,
    FileServiceImplTest.class,
    PollServiceImplTest.class
})
public class ServiceTestSuite {

}
