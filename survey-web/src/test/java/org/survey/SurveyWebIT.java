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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.PhantomJsDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.model.user.Role;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ServiceTestConfig.class))
@SuppressWarnings("squid:S2925")
public class SurveyWebIT {
    @PhantomJsDriver(version="1.9.7")
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
    private String httpPort;
    private String httpProtocol;
    private String serverURL;
    private String appName = "survey-web";
    @Resource
    protected UserService userService;
    @Resource
    protected PollService pollService;

    @Before
    public void setUp() {
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
        serverURL = httpProtocol + "://localhost:" + httpPort;
    }

    @After
    public void tearDown() {
        deletePollFromRepository("poll");
        deleteUserFromRepository("registered_user");
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
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

        loginError();
        registerNewUser("registered_user", "registered_user@test.com", "test");
        login("registered_user", "test");
        assertUserRole("registered_user", "User");
        assertNoDeleteOrAdd();
        logout();
        login("admin", "admin");
        deleteUser("registered_user");
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

    protected void openBrowser() {
        webDriver.get(serverURL + "/" + appName + "/");
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }

    protected void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='"
                + username + "']//span[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    protected void editUser(String username, String password)
            throws InterruptedException {
        WebElement button = webDriver.findElement(By
                .xpath("//tr[contains(td/text(),'" + username
                        + "')]//input[contains(@id,'edit')]"));
//        WebElement button = browser.findElement(By
//                .xpath("//input/@title,'" + username + "')]"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Edit user",
                webDriver.getTitle());
        Assert.assertEquals(username, webDriver.findElement(By.id("editUser:editUser:username")).getAttribute("value"));
        webDriver.findElement(By.id("editUser:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("editUser:editUser:repeatPassword"))
                .sendKeys(password);
        // selectItemInList("editUser:editUser:role", Role.ROLE_USER.name());
        webDriver.findElement(By.id("editUser:update")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    protected void selectItemInList(String elementId, String value) {
        WebElement roleSelect = webDriver.findElement(By.id(elementId));
        List<WebElement> options = roleSelect
                .findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                option.click();
            }
        }
    }

    protected void addUser(String username, String email, String password,
            Role role) {
        webDriver.findElement(By.id("users:addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add user",
                webDriver.getTitle());
        webDriver.findElement(By.id("addUser:editUser:username")).sendKeys(
                username);
        webDriver.findElement(By.id("addUser:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("addUser:editUser:repeatPassword")).sendKeys(
                password);
        webDriver.findElement(By.id("addUser:editUser:email")).sendKeys(email);
        selectItemInList("addUser:editUser:role", role.name());
        webDriver.findElement(By.id("addUser:addUser")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    protected void deleteUser(String username) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + username
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    protected void assertNoDeleteOrAdd() {
        try {
            webDriver.findElement(By.xpath("//tr//input[@value='Delete']"));
            Assert.fail();
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    protected void registerNewUser(String username, String email,
            String password) {
        webDriver.findElement(By.id("register:register")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Register",
                webDriver.getTitle());
        webDriver.findElement(By.id("register:editUser:username")).sendKeys(
                username);
        webDriver.findElement(By.id("register:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("register:editUser:repeatPassword"))
                .sendKeys(password);
        webDriver.findElement(By.id("register:editUser:email")).sendKeys(email);
        webDriver.findElement(By.id("register:registerButton")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }

    protected void checkVersion() {
        Assert.assertTrue(webDriver.getPageSource(), webDriver.getPageSource().contains("1.4-SNAPSHOT"));
    }

    private void addPoll(String pollName) throws InterruptedException {
        webDriver.findElement(By.id("menu:menu-show-polls")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
        webDriver.findElement(By.id("polls:addPoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("addPoll:pollName")).sendKeys(pollName);
        webDriver.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }

    private void editPoll(String pollName) {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Edit poll']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
    }

    private void addQuestion(String questionText) {
        webDriver.findElement(By.id("addPoll:addQuestion")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Add poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("addPoll:questions:0:questionText"))
                .sendKeys(questionText);
        webDriver.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }

    protected void deletePoll(String pollName) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }    

    protected void loginError() {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys("");
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys("");
        webDriver.findElement(By.id("loginButton")).click();
        Assert.assertEquals("Login", webDriver.getTitle());
        Assert.assertTrue(webDriver.getPageSource().contains("Login error"));
    }
    
    protected void login(String username, String password) {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys(username);
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys(password);
        webDriver.findElement(By.id("loginButton")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    public void logout() {
        webDriver.findElement(By.id("menu:logout")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }
}
