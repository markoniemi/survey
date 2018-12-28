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
import org.survey.model.poll.QuestionType;
import org.survey.model.user.Role;
import org.survey.model.user.User;
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
        webDriver.get(loginUrl);
        loginPage = new LoginPage(webDriver);
        usersPage = loginPage.login("admin1", "admin");
        userPage = usersPage.clickAddUser();
        usersPage = userPage.addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
        usersPage.assertUserRole("admin_user", "Admin");
        userPage = usersPage.clickAddUser();
        usersPage = userPage.addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
        usersPage.assertUserRole("user_user", "User");
        usersPage = usersPage.deleteUser("user_user");
        loginPage = usersPage.logout();
        usersPage = loginPage.login("admin_user", "another");
        userPage = usersPage.editUser("admin_user");
        usersPage = userPage.editUser("admin_user", "newpassword");
        loginPage = usersPage.logout();
        usersPage = loginPage.login("admin_user", "newpassword");
        usersPage = usersPage.deleteUser("admin_user");
        pollsPage = userPage.polls();
        pollPage = pollsPage.clickAddPoll();
        pollsPage = pollPage.addPoll("poll");
        pollPage = pollsPage.editPoll("poll");
        pollPage = pollPage.clickAddQuestion();
        pollsPage = pollPage.addQuestion("question1", QuestionType.LABEL);
        pollsPage = pollsPage.deletePoll("poll");
        loginPage = pollsPage.logout();
    }

    private void deletePollFromRepository(String name) {
        Poll poll = pollService.findByName(name);
        if (poll != null) {
            pollService.delete(poll.getId());
        }
    }

    private void deleteUserFromRepository(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            userService.delete(user.getId());
        }
    }
}
