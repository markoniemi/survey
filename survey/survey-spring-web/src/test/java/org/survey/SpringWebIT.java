package org.survey;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.model.user.Role;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
@SuppressWarnings("squid:S2925")
public class SpringWebIT {
    private static final int SLEEP_TIME = 1000;
//    DesiredCapabilities capabilities;
//    {
//        String phantomJsPath = System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//                "c:/apps/phantomjs-2.0.0-windows/bin/phantomjs.exe");
//        capabilities = new DesiredCapabilities();
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
//    }
//    private PhantomJSDriver browser = new PhantomJSDriver(capabilities);
    private PhantomJSDriver browser;
    private String httpPort;
    private String httpProtocol;
    private String serverURL;
    private String appName = "survey-spring-web";
    @Resource
    protected UserService userService;
    @Resource
    protected PollService pollService;
//    @Rule
//    public SeleniumTestRule screenshotTestRule = new SeleniumTestRule(browser);

    @Before
    public void setUp() {
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
        serverURL = httpProtocol + "://localhost:" + httpPort;
        
//        FirefoxProfile profile = new FirefoxProfile();
//        profile.setAcceptUntrustedCertificates(true);
//        profile.setAssumeUntrustedCertificateIssuer(false);
//        browser = new FirefoxDriver(profile);
        String phantomJsPath = System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "c:/apps/phantomjs-2.0.0-windows/bin/phantomjs.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
        browser = new PhantomJSDriver(capabilities);
//        screenshotTestRule.setBrowser(browser);
        // increase size to make navigation visible
        browser.manage().window().setSize(new Dimension(800, 600));
    }

    @After
    public void tearDown() {
        browser.close();
        browser.quit();
//        deleteUserFromRepository("registered_user");
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
        deletePollFromRepository("poll");
    }

    private void deletePollFromRepository(String pollName) {
        pollService.delete(pollName);
    }

    private void deleteUserFromRepository(String username) {
        userService.delete(username);
    }

    @Test
    public void integrationTest() throws InterruptedException {
        openBrowser();

//        loginError();
//        registerNewUser("registered_user", "registered_user@test.com", "test");
//        login("registered_user", "test");
//        assertUserRole("registered_user", "User");
//        assertNoDeleteOrAdd();
//        logout();
        login("admin", "admin");
//        deleteUser("registered_user");
        addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
        assertUserRole("admin_user", "Admin");
        addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
        assertUserRole("user_user", "User");
        deleteUser("user_user");
        logout();
        login("admin_user", "another");
        editUser("admin_user", "newpassword");
        logout();
        login("admin_user", "newpassword");
        deleteUser("admin_user");
//        checkVersion();
         addPoll("poll");
         editPoll("poll");
         addQuestion("question1");
        deletePoll("poll");
        logout();
    }

    protected void openBrowser() throws InterruptedException {
        browser.get(serverURL + "/" + appName);
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }

    protected void assertUserRole(String username, String localizedRole) {
        WebElement element = browser.findElement(By.xpath("//tr[td='"
                + username + "']//td[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    protected void editUser(String username, String password)
            throws InterruptedException {
        WebElement button = browser.findElement(By
                .xpath("//tr[contains(td/text(),'" + username
                        + "')]//a[contains(@id,'edit')]"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "User",
                browser.getTitle());
        WebElement passwordElement = browser.findElement(By.id("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
//        WebElement repeatPasswordElement = browser.findElement(By.id("repeatPassword"));
//        repeatPasswordElement.clear();
//        repeatPasswordElement.sendKeys(password);
        selectItemInList("role", Role.ROLE_USER.name());
        browser.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/*browser.getPageSource(),*/ "Users",
                browser.getTitle());
    }

    protected void selectItemInList(String elementId, String value) {
        WebElement roleSelect = browser.findElement(By.id(elementId));
        List<WebElement> options = roleSelect
                .findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                option.click();
            }
        }
    }

    protected void addUser(String username, String email, String password,
            Role role) throws InterruptedException {
        browser.findElement(By.id("addUser")).click();
//        browser.get(serverURL + "/" + appName + "/#/users/user");
//        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "User",
                browser.getTitle());
        browser.findElement(By.id("username")).sendKeys(
                username);
        browser.findElement(By.id("password")).sendKeys(
                password);
//        browser.findElement(By.id("repeatPassword")).sendKeys(
//                password);
        browser.findElement(By.id("email")).sendKeys(email);
        selectItemInList("role", role.name());
        browser.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/*browser.getPageSource(),*/ "Users",
                browser.getTitle());
    }

    protected void deleteUser(String username) throws InterruptedException {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + username
                + "']//button[@id='delete']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Users",
                browser.getTitle());
    }

    protected void assertNoDeleteOrAdd() {
        try {
            browser.findElement(By.xpath("//tr//button[@id='delete']"));
            Assert.fail();
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    protected void registerNewUser(String username, String email,
            String password) {
        browser.findElement(By.id("register:register")).click();
        Assert.assertEquals(browser.getPageSource(), "Register",
                browser.getTitle());
        browser.findElement(By.id("register:editUser:username")).sendKeys(
                username);
        browser.findElement(By.id("register:editUser:password")).sendKeys(
                password);
        browser.findElement(By.id("register:editUser:repeatPassword"))
                .sendKeys(password);
        browser.findElement(By.id("register:editUser:email")).sendKeys(email);
        browser.findElement(By.id("register:registerButton")).click();
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }

    protected void checkVersion() {
        Assert.assertTrue(browser.getPageSource(), browser.getPageSource().contains("1.4-SNAPSHOT"));
    }

    private void addPoll(String pollName) throws InterruptedException {
        browser.findElement(By.id("menu-polls")).click();
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
        // TODO find a fix for click not working with maven build
        browser.findElement(By.id("addPoll")).click();
//        browser.get(serverURL + "/" + appName + "/#/polls/poll");
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Poll",
                browser.getTitle());
        browser.findElement(By.id("pollName")).sendKeys(pollName);
        browser.findElement(By.id("savePoll")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }

    private void editPoll(String pollName) {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + pollName
                + "']//a[@id='edit']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Assert.assertEquals(browser.getPageSource(), "Poll",
                browser.getTitle());
        // TODO remove when addQuestion works
//        browser.findElement(By.id("savePoll")).click();
//        Assert.assertEquals(browser.getPageSource(), "Polls",
//                browser.getTitle());
    }

    private void addQuestion(String questionText) {
        browser.findElement(By.id("addQuestion")).click();
        Assert.assertEquals(browser.getPageSource(), "Poll",
                browser.getTitle());
        browser.findElement(By.id("questionText"))
                .sendKeys(questionText);
        browser.findElement(By.id("savePoll")).click();
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }

    protected void deletePoll(String pollName) throws InterruptedException {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + pollName
                + "']//button[@id='delete']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }    

    protected void loginError() throws InterruptedException {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys("");
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys("");
        browser.findElement(By.id("loginButton")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals("Login", browser.getTitle());
        Assert.assertTrue(browser.getPageSource().contains("Login error"));
    }
    
    protected void login(String username, String password) throws InterruptedException {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys(username);
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys(password);
        browser.findElement(By.id("loginButton")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Users",
                browser.getTitle());
    }

    public void logout() throws InterruptedException {
        browser.findElement(By.id("logout")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }
}
