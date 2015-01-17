package org.survey;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.model.user.Role;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class WebDriverIT {
    private HtmlUnitDriver browser;
//    private WebDriver browser;
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
        // browser = new FirefoxDriver();
//        browser = new HtmlUnitDriver();
//        browser = new HtmlUnitDriver(BrowserVersion.CHROME);
//        browser = new HtmlUnitDriver(true);
        browser = new HtmlUnitDriver();
        browser.setJavascriptEnabled(true);
//        browser = new ChromeDriver();
    }

    @After
    public void tearDown() {
        browser.close();
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
        checkVersion();
         addPoll("poll");
         editPoll("poll");
         addQuestion("question1");
        deletePoll("poll");
        logout();
    }

    protected void openBrowser() {
        browser.get(serverURL + "/" + appName + "/");
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }

    protected void assertUserRole(String username, String localizedRole) {
        WebElement element = browser.findElement(By.xpath("//tr[td='"
                + username + "']//span[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    protected void editUser(String username, String password)
            throws InterruptedException {
        WebElement button = browser.findElement(By
                .xpath("//tr[contains(td/text(),'" + username
                        + "')]//input[contains(@id,'edit')]"));
//        WebElement button = browser.findElement(By
//                .xpath("//input/@title,'" + username + "')]"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(browser.getPageSource(), "Edit user",
                browser.getTitle());
        Assert.assertEquals(username, browser.findElement(By.id("editUser:editUser:username")).getAttribute("value"));
        browser.findElement(By.id("editUser:editUser:password")).sendKeys(
                password);
        browser.findElement(By.id("editUser:editUser:repeatPassword"))
                .sendKeys(password);
        // selectItemInList("editUser:editUser:role", Role.ROLE_USER.name());
        browser.findElement(By.id("editUser:update")).click();
        Assert.assertEquals(browser.getPageSource(), "Users",
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
            Role role) {
        browser.findElement(By.id("users:addUser")).click();
        Assert.assertEquals(browser.getPageSource(), "Add user",
                browser.getTitle());
        browser.findElement(By.id("addUser:editUser:username")).sendKeys(
                username);
        browser.findElement(By.id("addUser:editUser:password")).sendKeys(
                password);
        browser.findElement(By.id("addUser:editUser:repeatPassword")).sendKeys(
                password);
        browser.findElement(By.id("addUser:editUser:email")).sendKeys(email);
        selectItemInList("addUser:editUser:role", role.name());
        browser.findElement(By.id("addUser:addUser")).click();
        Assert.assertEquals(browser.getPageSource(), "Users",
                browser.getTitle());
    }

    protected void deleteUser(String username) throws InterruptedException {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + username
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(browser.getPageSource(), "Users",
                browser.getTitle());
    }

    protected void assertNoDeleteOrAdd() {
        try {
            browser.findElement(By.xpath("//tr//input[@value='Delete']"));
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
        browser.findElement(By.id("menu:menu-show-polls")).click();
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
        browser.findElement(By.id("polls:addPoll")).click();
        Assert.assertEquals(browser.getPageSource(), "Add poll",
                browser.getTitle());
        browser.findElement(By.id("addPoll:pollName")).sendKeys(pollName);
        browser.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }

    private void editPoll(String pollName) {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Edit poll']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Assert.assertEquals(browser.getPageSource(), "Add poll",
                browser.getTitle());
    }

    private void addQuestion(String questionText) {
        browser.findElement(By.id("addPoll:addQuestion")).click();
        Assert.assertEquals(browser.getPageSource(), "Add poll",
                browser.getTitle());
        browser.findElement(By.id("addPoll:questions:0:questionText"))
                .sendKeys(questionText);
        browser.findElement(By.id("addPoll:savePoll")).click();
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }

    protected void deletePoll(String pollName) throws InterruptedException {
        WebElement button = browser.findElement(By.xpath("//tr[td='" + pollName
                + "']//input[@value='Delete']"));
        Assert.assertNotNull(browser.getPageSource(), button);
        button.click();
        Thread.sleep(2000);
        Assert.assertEquals(browser.getPageSource(), "Polls",
                browser.getTitle());
    }    

    protected void loginError() {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys("");
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys("");
        browser.findElement(By.id("loginButton")).click();
        Assert.assertEquals("Login", browser.getTitle());
        Assert.assertTrue(browser.getPageSource().contains("Login error"));
    }
    
    protected void login(String username, String password) {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys(username);
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys(password);
        browser.findElement(By.id("loginButton")).click();
        Assert.assertEquals(browser.getPageSource(), "Users",
                browser.getTitle());
    }

    public void logout() {
        browser.findElement(By.id("menu:logout")).click();
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }
}
