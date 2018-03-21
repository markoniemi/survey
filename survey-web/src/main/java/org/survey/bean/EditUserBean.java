package org.survey.bean;

import javax.annotation.Resource;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;
import org.survey.FacesUtil;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@ViewScoped
@Slf4j
@SuppressWarnings("PMD.UnusedPrivateField")
public class EditUserBean {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private String repeatedPassword;
    @Setter
    @Resource
    UserService userService;

    public String addUser() {
        user = new User();
        user.setRole(Role.ROLE_USER);
        return "addUser";
    }
    
    public String editUser() {
        username = getRequestParameter("username");
        log.debug("editUser(): username: {}", username);
        if (username != null) {
            user = userService.findOne(username);
            repeatedPassword = user.getPassword();
            log.debug("editUser(): user: {}", user);
        }
        return "editUser";
    }

    /**
     * Called from editUser.xhtml, addUser.xhtml and register.xhtml page when
     * user presses Update button.
     */
    public String saveUser() {
        log.info(user.toString());
        if (!repeatedPassword.equals(user.getPassword())) {
            showMessage(null, "repeatedPasswordError", null);
            return null;
        }
        try {
            if (user.getId() == null) {
                userService.create(user);
            } else {
                userService.update(user);
            }
        } catch (IllegalArgumentException e) {
            log.debug("Unable to create user, a user with same name already exists: {]", user.getUsername());
            showMessage(null, "userExists", e);
            return null;
        }
        return "userSaved";
    }

    protected String getRequestParameter(String parameterName) {
        return FacesUtil.getRequestParameter(parameterName);
    }
    
    /**
     * showMessage is package private to enable overriding in a test case.
     */
    void showMessage(String id, String messageKey, Exception e) {
        log.error(messageKey, e);
        FacesUtil.showMessage(id, messageKey);
    }

    /**
     * Roles for role dropdown in editUser.xhtml.
     */
    public Role[] getRoles() {
        return Role.values();
    }
}
