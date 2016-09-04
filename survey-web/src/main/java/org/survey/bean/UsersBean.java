package org.survey.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

@Component
@Scope("request")
public class UsersBean {
    @Getter
    @Setter
    @Resource
    private UserService userService;
    @Getter
    @SuppressWarnings("checkstyle.com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck")
    List<User> users;

    @PostConstruct
    public void initialize() {
        User[] users = userService.findAll();
        if (users != null) {
            this.users = Arrays.asList(users);
        }
    }

    /**
     * Called when user presses Delete button on users.xhtml.
     */
    // @RolesAllowed({"ROLE_ADMIN"})
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser() {
        userService.delete(getRequestParameter("username"));
        initialize();
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }
}
