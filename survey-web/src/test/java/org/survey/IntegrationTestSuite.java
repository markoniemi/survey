package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    UnitTestSuite.class, 
    HtmlUnitIT.class, 
    WebDriverIT.class,
// need to re-write the service tests so that they do not use repositories
//    PollServiceIT.class
})
public class IntegrationTestSuite {
}
