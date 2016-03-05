package org.survey.selenium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import lombok.Getter;
import lombok.Setter;

/**
 * @see http://blogs.steeplesoft.com/posts/2012/grabbing-screenshots-of-failed-
 *      selenium-tests.html
 * @see https://thomassundberg.wordpress.com/2012/07/08/performing-an-action-
 *      when-a-test-fails/
 */
class SeleniumTestRule extends TestWatcher {

    @Setter
    private WebDriver webDriver;
    private String screenshotDirectory = "target/failsafe-reports/";
    private Object testCase;
    private boolean createSubdirectoryForTestCase;

    public SeleniumTestRule(Object testCase) {
        this(testCase, "target/failsafe-reports/", true);
    }

    public SeleniumTestRule(Object testCase, String screenshotDirectory, boolean createSubdirectoryForTestCase) {
        this(testCase, null, screenshotDirectory, createSubdirectoryForTestCase);
    }

    public SeleniumTestRule(Object testCase, WebDriver webDriver, String screenshotDirectory,
            boolean createSubdirectoryForTestCase) {
        this.testCase = testCase;
        this.webDriver = webDriver;
        this.screenshotDirectory = screenshotDirectory;
        this.createSubdirectoryForTestCase = createSubdirectoryForTestCase;
    }

    @Override
    protected void failed(Throwable throwable, Description description) {
        try {
            createDirectory(screenshotDirectory);
            String testName = getTestName(description);
            writeScreenshot(testName);
            writeBrowserPage(testName);
        } catch (Exception e) {
            // No need to crash the tests if the screenshot fails
        }
    }

    private void writeBrowserPage(String testName) throws FileNotFoundException, IOException {
        FileOutputStream sourceOutputStream = new FileOutputStream(screenshotDirectory + "/" + testName + ".html");
        sourceOutputStream.write(webDriver.getPageSource().getBytes());
        sourceOutputStream.close();
    }

    private void writeScreenshot(String testName) throws FileNotFoundException, IOException {
        FileOutputStream screenshotOutputStream = new FileOutputStream(screenshotDirectory + "/" + testName + ".png");
        screenshotOutputStream.write(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES));
        screenshotOutputStream.close();
    }

    private String getTestName(Description description) {
        return description.getClassName() + "-" + description.getMethodName();
    }

    private void createDirectory(String screenshotDirectory2) {
        new File(screenshotDirectory).mkdirs();
    }

    @Override
    protected void finished(Description description) {
        webDriver.close();
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            getWebDriverFromAnnotation();
        }
        return this.webDriver;
    }

    protected void getWebDriverFromAnnotation() {
        for (Field field : FieldUtils.getFieldsWithAnnotation(this.testCase.getClass(), org.survey.selenium.WebDriver.class)) {
            try {
                if(field.get(this.testCase)!=null);
            } catch (IllegalArgumentException e) {
                // suppress errors quietly
            } catch (IllegalAccessException e) {
                // suppress errors quietly
            }
        }
    }
}