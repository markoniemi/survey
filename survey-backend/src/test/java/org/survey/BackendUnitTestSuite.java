package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.survey.security.JwtAuthenticationFilterTest;
import org.survey.security.JwtTokenTest;
import org.survey.service.login.LoginServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    JwtAuthenticationFilterTest.class,
    JwtTokenTest.class,
    LoginServiceTest.class
})
public class BackendUnitTestSuite {
}
