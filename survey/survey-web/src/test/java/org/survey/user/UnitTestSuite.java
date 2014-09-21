package org.survey.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.user.bean.EditPollBeanTest;
import org.survey.user.bean.EditUserBeanTest;
import org.survey.user.bean.FileUploadBeanTest;
import org.survey.user.bean.FilesBeanTest;
import org.survey.user.bean.MediaBeanTest;
import org.survey.user.bean.PollsBeanTest;
import org.survey.user.bean.UsersBeanTest;
import org.survey.user.bean.VersionBeanTest;
import org.survey.user.security.UserRepositoryAuthenticationProviderTest;
import org.survey.user.validator.EmailValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    EmailValidatorTest.class,
    UserRepositoryAuthenticationProviderTest.class,
    UsersBeanTest.class, 
    EditUserBeanTest.class,
    FilesBeanTest.class,
    MediaBeanTest.class,
    FileUploadBeanTest.class,
    VersionBeanTest.class,
    PollsBeanTest.class,
    EditPollBeanTest.class,
})
public class UnitTestSuite {
}
