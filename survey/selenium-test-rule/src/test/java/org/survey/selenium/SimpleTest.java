package org.survey.selenium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
@Ignore
public class SimpleTest {
    private String message = "test";
    @Rule
    public FailedTestWatcher failedTestRule = new FailedTestWatcher(message);

    @Before
    public void setUp() {
    }

    @Test
    public void test() {
        Assert.fail();
    }

    @Test
    public void anotherTest() {
        failedTestRule.setMessage("anotherMessage");
        Assert.fail();
    }
}
