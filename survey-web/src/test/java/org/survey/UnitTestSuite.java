package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.bean.EditPollBeanTest;
import org.survey.bean.EditUserBeanTest;
import org.survey.bean.FilesBeanTest;
import org.survey.bean.MediaBeanTest;
import org.survey.bean.PollsBeanTest;
import org.survey.bean.UsersBeanTest;
import org.survey.bean.VersionBeanTest;
import org.survey.security.UserRepositoryAuthenticationProviderTest;
import org.survey.validator.EmailValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    EmailValidatorTest.class,
    UserRepositoryAuthenticationProviderTest.class,
    UsersBeanTest.class, 
    EditUserBeanTest.class,
    FilesBeanTest.class,
    MediaBeanTest.class,
//    FileUploadBeanTest.class,
    VersionBeanTest.class,
    PollsBeanTest.class,
    EditPollBeanTest.class,
})
public class UnitTestSuite {
}
