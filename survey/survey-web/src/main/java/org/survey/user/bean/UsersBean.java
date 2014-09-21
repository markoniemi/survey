package org.survey.user.bean;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.survey.user.FacesUtil;
import org.survey.user.model.User;
import org.survey.user.service.UserService;

@Component
@Scope("request")
public class UsersBean {
    @Getter
    @Setter
    @Autowired
    private UserService userService;
    @Getter
    @SuppressWarnings("checkstyle.com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck")
    User[] users;

    @PostConstruct
    public void initialize() {
        users = userService.findAll();
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
