package org.survey.user;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FileServiceWebDriverIT {
    private WebDriver browser;
    private String httpPort;
    private String httpProtocol;

    @Before
    public void setUp() {
        // create HtmlUnitDriver or FireFoxDriver
        browser = new HtmlUnitDriver(true);
        // get tomcat port from system property
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
    }
    
    @After
    public void tearDown() {
        browser.close();
    }

    @Ignore
    @Test
    public void testWsdl() {
        // load WSDL page
        browser.get(httpProtocol + "://localhost:" + httpPort
                + "/survey-web/api/soap/fileService?wsdl");
        // check that the page contains some valid WSDL
        Assert.assertTrue(browser.getPageSource(), browser.getPageSource()
                .contains("resource path=\"/files\""));    }
}
