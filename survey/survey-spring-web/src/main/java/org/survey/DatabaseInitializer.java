package org.survey;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.survey.model.poll.Poll;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DatabaseInitializer {

    @Setter
    @Resource(name="userServiceBean")
    private transient UserService userService;
    @Setter
    @Resource(name="pollServiceBean")
    private transient PollService pollService;

    @PostConstruct
    public void initialize() {
        log.debug("DatabaseInitializer.initialize");
        try {
            userService.create(new User("admin", "admin", "admin@test.com", Role.ROLE_ADMIN));
            pollService.create(new Poll("poll"));
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
        }
    }
}
