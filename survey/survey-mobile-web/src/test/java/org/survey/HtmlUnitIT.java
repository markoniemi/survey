package org.survey;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.user.model.Role;
import org.survey.user.service.UserService;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-rest-service-test.xml")
public class HtmlUnitIT {
    private String httpPort;
    private String httpProtocol;
    private String serverURL;
    private String appName = "survey-mobile-web";
    private WebClient webClient;
    private HtmlPage page;
    @Autowired
    protected UserService userService;
//    @Autowired
//    protected PollService pollService;

    @Before
    public void setUp() throws MalformedURLException, GeneralSecurityException {
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
        serverURL = httpProtocol + "://localhost:" + httpPort;
        
//        webClient = new WebClient();
        webClient = new WebClient(BrowserVersion.FIREFOX_17);
        final List<String> collectedAlerts = new ArrayList<String>();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setRedirectEnabled(false);
//        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setTimeout(10000);
//        webClient.getOptions().setActiveXNative(false);
//        webClient.getOptions().setUseInsecureSSL(true);
//
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(true);
//        webClient.getOptions().setPrintContentOnFailingStatusCode(true);        
//        webClient.setJavaScriptEnabled(true);
//        webClient.setJavaScriptTimeout(10000);
//        webClient.setTimeout(40000);
//        File keyStore = new File("src/test/resources/survey.jks");
//        URL url = keyStore.toURL();
//        webClient.setSSLClientCertificate(url, "changeit", "JKS");
//        webClient.setUseInsecureSSL(true);
    }

    @After
    public void tearDown() throws Exception {
        webClient.closeAllWindows();
//        deleteUserFromRepository("registered_user");
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
//        deletePollFromRepository("poll");
    }

//    private void deletePollFromRepository(String pollName) {
//        pollService.delete(pollName);
//    }

    private void deleteUserFromRepository(String username) {
        userService.delete(username);
    }

    @Test
    public void integrationTest() throws IOException, InterruptedException {
        openBrowser();

//        loginError();
//        registerNewUser("registered_user", "registered_user@test.com", "test");
//        login("registered_user", "test");
//        assertUserRole("registered_user", "User");
//        assertNoDeleteOrAdd();
//        logout();
//        login("admin", "admin");
//        deleteUser("registered_user");
        addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
//        assertUserRole("admin_user", "Admin");
        addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
//        assertUserRole("user_user", "User");
        deleteUser("user_user");
        // TODO test error case
        //addUserWithError("");
//        logout();
//        login("admin_user", "another");
        editUser("admin_user", "newpassword");
//        logout();
//        login("admin_user", "newpassword");
        deleteUser("admin_user");
//        checkVersion();
//        addPoll("poll");
//        editPoll("poll");
//        addQuestion("question1");
//        deletePoll("poll");
//        logout();
    }

    protected void openBrowser() throws IOException, MalformedURLException, InterruptedException {
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        page = webClient.getPage(serverURL + "/" + appName + "/index.html");
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        webClient.waitForBackgroundJavaScript(10000);
        Thread.sleep(2000);
        Assert.assertEquals("Users", page.getTitleText());
    }

    protected void assertUserRole(String username, String localizedRole) {
        // TODO use MessageFormat and constant for xpath
        // MessageFormat.format()
        HtmlElement element = (HtmlElement) page.getFirstByXPath("//tr[td='"
                + username + "']//td[contains(@id,'role')]");
        Assert.assertNotNull(element);
        System.out.println(element.getTextContent());
        Assert.assertEquals(localizedRole, element.getTextContent());
    }

    protected void editUser(String username, String password)
            throws IOException, InterruptedException {
        HtmlInput button = (HtmlInput) page
                .getFirstByXPath("//tr[contains(td/text(),'" + username
                        + "')]//input[contains(@id,'edit')]");
        Assert.assertNotNull(button);
        page = button.click();
        Thread.sleep(2000);
        HtmlForm form = page.getFormByName("editUser");
        HtmlInput passwordInput = form
                .getInputByName("editUser:editUser:password");
        passwordInput.setValueAttribute(password);
        HtmlInput passwordRepeat = form
                .getInputByName("editUser:editUser:repeatPassword");
        passwordRepeat.setValueAttribute(password);
        // HtmlSelect roleSelect =
        // form.getSelectByName("editUser:editUser:role");
        // roleSelect.getOptionByValue(Role.ROLE_USER.name()).click();
        // Assert.assertEquals(Role.ROLE_USER.name(), roleSelect
        // .getSelectedOptions().get(0).getValueAttribute());
        HtmlInput updateButton = form.getInputByName("editUser:update");
        page = updateButton.click();
    }

    protected void addUser(String username, String email, String password,
            Role role) throws IOException, InterruptedException {
//        HtmlForm form = page.getFormByName("users");
        HtmlElement addUsersLink = page.getHtmlElementById("addUser");
        page = addUsersLink.click();
        Thread.sleep(2000);
        Assert.assertEquals(page.asXml(), "User", page.getTitleText());
        HtmlForm form = page.getFormByName("editUser");
        HtmlInput usernameInput = form
                .getInputByName("username");
        usernameInput.setValueAttribute(username);
        HtmlInput passwordInput = form
                .getInputByName("password");
        passwordInput.setValueAttribute(password);
        HtmlInput passwordRepeat = form
                .getInputByName("repeatPassword");
        passwordRepeat.setValueAttribute(password);
        HtmlInput emailInput = form.getInputByName("email");
        emailInput.setValueAttribute(email);
        HtmlSelect roleSelect = form.getSelectByName("role");
        log.debug(roleSelect.asXml());
        roleSelect.getOptionByValue(role.name()).click();
        Assert.assertEquals(role.name(), roleSelect.getSelectedOptions().get(0)
                .getValueAttribute());
        HtmlInput addButton = form.getInputByName("submit");
        page = addButton.click();
        Thread.sleep(2000);
    }

    protected void deleteUser(String username) throws IOException,
            InterruptedException {
        HtmlInput button = (HtmlInput) page.getFirstByXPath("//tr[td='"
                + username + "']//input[@value='Delete']");
        Assert.assertNotNull(page.asXml(), button);
        page = button.click();
        Thread.sleep(2000);
        Assert.assertEquals(page.asText(), "Users", page.getTitleText());
    }

    protected void assertNoDeleteOrAdd() {
        HtmlInput button = (HtmlInput) page
                .getFirstByXPath("//tr//input[@value='Delete']");
        Assert.assertNull(page.asXml(), button);
    }

    protected void checkVersion() {
        Assert.assertTrue(page.asXml(), page.asXml().contains("1.3-SNAPSHOT"));
    }

    private void addPoll(String pollName) throws ElementNotFoundException,
            IOException {
//        HtmlForm form = page.getFormByName("menu");
        page = page.getHtmlElementById("menu:menu-show-polls").click();
        Assert.assertEquals("Polls", page.getTitleText());
        HtmlForm form = page.getFormByName("polls");
        page = form.getInputByName("polls:addPoll").click();
        Assert.assertEquals("Add poll", page.getTitleText());
        form = page.getFormByName("addPoll");
        form.getInputByName("addPoll:pollName").setValueAttribute(pollName);
        page = form.getInputByName("addPoll:savePoll").click();
        Assert.assertEquals("Polls", page.getTitleText());
    }

    private void editPoll(String pollName) throws IOException,
            InterruptedException {
        HtmlInput button = (HtmlInput) page.getFirstByXPath("//tr[td='"
                + pollName + "']//input[@value='Edit poll']");
        Assert.assertNotNull(button);
        page = button.click();
        Assert.assertEquals(page.asXml(), "Add poll", page.getTitleText());
    }

    private void addQuestion(String questionText)
            throws ElementNotFoundException, IOException {
        page = page.getFormByName("addPoll").getInputByName("addPoll:addQuestion").click();
        Assert.assertEquals("Add poll", page.getTitleText());
        HtmlForm form = page.getFormByName("addPoll");
        form.getInputByName("addPoll:questions:0:questionText")
                .setValueAttribute(questionText);
        page = form.getInputByName("addPoll:savePoll").click();
        Assert.assertEquals("Polls", page.getTitleText());
    }

    private void deletePoll(String pollName) throws IOException, InterruptedException {
        HtmlInput button = (HtmlInput) page.getFirstByXPath("//tr[td='"
                + pollName + "']//input[@value='Delete']");
        Assert.assertNotNull(page.asXml(), button);
        page = button.click();
        Thread.sleep(2000);
        Assert.assertEquals(page.asXml(), "Polls", page.getTitleText());
        // TODO check that poll has been deleted
    }

    protected void registerNewUser(String username, String email,
            String password) throws IOException {
        HtmlForm form = page.getFormByName("register");
        HtmlElement registerLink = form.getInputByName("register:register");
        page = registerLink.click();
        Assert.assertEquals("Register", page.getTitleText());
        form = page.getFormByName("register");
        HtmlInput usernameInput = form
                .getInputByName("register:editUser:username");
        usernameInput.setValueAttribute(username);
        HtmlInput passwordInput = form
                .getInputByName("register:editUser:password");
        passwordInput.setValueAttribute(password);
        HtmlInput passwordRepeat = form
                .getInputByName("register:editUser:repeatPassword");
        passwordRepeat.setValueAttribute(password);
        HtmlInput emailInput = form.getInputByName("register:editUser:email");
        emailInput.setValueAttribute(email);
        HtmlInput registerButton = form
                .getInputByName("register:registerButton");
        page = registerButton.click();
        Assert.assertEquals(page.asText(), "Login", page.getTitleText());
    }

    protected void logout() throws IOException {
//        HtmlForm form = page.getFormByName("menu");
        page = page.getHtmlElementById("menu:logout").click();
        Assert.assertEquals("Login", page.getTitleText());
    }

    protected void loginError() throws IOException,
    InterruptedException {
        HtmlForm form = page.getFormByName("loginForm");
        HtmlInput usernameInput = form.getInputByName("j_username");
        usernameInput.setValueAttribute("");
        HtmlInput passwordInput = form.getInputByName("j_password");
        passwordInput.setValueAttribute("");
        HtmlInput loginButton = form.getInputByName("loginButton");
        page = loginButton.click();
        Assert.assertEquals("Login", page.getTitleText());
        Assert.assertTrue(page.asText().contains("Login error"));
    }
    
    protected void login(String username, String password) throws IOException,
            InterruptedException {
        HtmlForm form = page.getFormByName("loginForm");
        HtmlInput usernameInput = form.getInputByName("j_username");
        usernameInput.setValueAttribute(username);
        HtmlInput passwordInput = form.getInputByName("j_password");
        passwordInput.setValueAttribute(password);
        HtmlInput loginButton = form.getInputByName("loginButton");
        page = loginButton.click();
        Assert.assertEquals("Users", page.getTitleText());
    }
}
