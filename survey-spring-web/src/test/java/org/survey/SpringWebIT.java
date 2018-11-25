package org.survey;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.config.ServiceTestConfig;
import org.survey.model.poll.Poll;
import org.survey.model.user.Role;
import org.survey.selenium.LoginPage;
import org.survey.selenium.PollPage;
import org.survey.selenium.PollsPage;
import org.survey.selenium.SeleniumConfig;
import org.survey.selenium.UserPage;
import org.survey.selenium.UsersPage;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = { ServiceTestConfig.class, SeleniumConfig.class }))
public class SpringWebIT {
    @Resource
    protected UserService userService;
    @Resource
    protected PollService pollService;
    @Resource(name = "loginUrl")
    private String loginUrl;
    @Resource
    protected WebDriver webDriver;
    private LoginPage loginPage;
    private UsersPage usersPage;
    private UserPage userPage;
    private PollsPage pollsPage;
    private PollPage pollPage;

    @Before
    public void setUp() {
        loginPage = new LoginPage(webDriver);
        usersPage = new UsersPage(webDriver);
        userPage = new UserPage(webDriver);
        pollsPage = new PollsPage(webDriver);
        pollPage = new PollPage(webDriver);
    }

    @After
    public void tearDown() {
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
        deletePollFromRepository("poll");
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void integrationTest() throws InterruptedException {
        loginPage.open(loginUrl);
        loginPage.login("admin1", "admin");
        usersPage.clickAddUser();
        userPage.addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
        usersPage.assertUserRole("admin_user", "Admin");
        usersPage.clickAddUser();
        userPage.addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
        usersPage.assertUserRole("user_user", "User");
        usersPage.deleteUser("user_user");
        usersPage.logout();
        loginPage.login("admin_user", "another");
        usersPage.editUser("admin_user");
        userPage.editUser("admin_user", "newpassword");
        usersPage.logout();
        loginPage.login("admin_user", "newpassword");
        usersPage.deleteUser("admin_user");
        pollsPage.clickAddPoll();
        pollPage.addPoll("poll");
        pollsPage.editPoll("poll");
        pollPage.addQuestion("question1");
        pollsPage.deletePoll("poll");
        pollsPage.logout();
    }

    private void deletePollFromRepository(String name) {
        Poll poll = pollService.findByName(name);
        if (poll != null) {
            pollService.delete(poll.getId());
        }
    }

    private void deleteUserFromRepository(String username) {
        userService.delete(username);
    }
}
