package org.survey;

import java.util.Arrays;
import java.util.List;

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
import org.survey.model.file.File;
import org.survey.model.poll.Poll;
import org.survey.model.poll.QuestionType;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.selenium.FilePage;
import org.survey.selenium.FilesPage;
import org.survey.selenium.LoginPage;
import org.survey.selenium.PollPage;
import org.survey.selenium.PollsPage;
import org.survey.selenium.SeleniumConfig;
import org.survey.selenium.UserPage;
import org.survey.selenium.UsersPage;
import org.survey.service.file.FileService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = { ServiceTestConfig.class, SeleniumConfig.class }))
public class SpringWebIT {
    @Resource
    protected UserService userService;
    @Resource
    protected PollService pollService;
    @Resource
    protected FileService fileService;
    @Resource(name = "loginUrl")
    private String loginUrl;
    @Resource
    protected WebDriver webDriver;
    private LoginPage loginPage;
    private UsersPage usersPage;
    private UserPage userPage;
    private PollsPage pollsPage;
    private PollPage pollPage;
    private FilesPage filesPage;
    private FilePage filePage;

    @Before
    public void setUp() {
        loginPage = new LoginPage(webDriver);
        usersPage = new UsersPage(webDriver);
        userPage = new UserPage(webDriver);
        pollsPage = new PollsPage(webDriver);
        pollPage = new PollPage(webDriver);
        filesPage = new FilesPage(webDriver);
        filePage = new FilePage(webDriver);
    }

    @After
    public void tearDown() {
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
        deletePollFromRepository("poll");
        deleteFileFromRepository("dummy.txt");
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void integrationTest() throws InterruptedException {
        webDriver.get(loginUrl);
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
        usersPage.polls();
        pollsPage.clickAddPoll();
        pollPage.addPoll("poll");
        pollsPage.editPoll("poll");
        pollPage.clickAddQuestion();
        pollPage.addQuestion("question1", QuestionType.LABEL);
        pollsPage.deletePoll("poll");
        pollsPage.files();
        filesPage.clickAddFile();
        filePage.addFile("dummy.txt");
        filesPage.assertFile("dummy.txt");
        pollsPage.logout();
    }

    private void deletePollFromRepository(String name) {
        Poll poll = pollService.findByName(name);
        if (poll != null) {
            pollService.delete(poll.getId());
        }
    }

    private void deleteFileFromRepository(String filename) {
        List<File> files = Arrays.asList(fileService.findAll());
        for (File file : files) {
            if (file != null && filename.equals(file.getFilename())) {
                fileService.delete(file.getId());
            }
        }
    }

    private void deleteUserFromRepository(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            userService.delete(user.getId());
        }
    }
}
