package org.survey.controller.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.survey.controller.UserValidator;
import org.survey.model.user.Role;
import org.survey.model.user.User;

public class UserValidatorTest {

    @Test
    public void validate() {
        UserValidator userValidator = new UserValidator();
        User user = new User("username", "password", "email", Role.ROLE_USER);
        Errors errors = new BindException(user, "user");
        userValidator.validate(user, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void validateWithErrors() {
        UserValidator userValidator = new UserValidator();
        User user = new User();
        Errors errors = new BindException(user, "user");
        userValidator.validate(user, errors);
        Assert.assertEquals(3, errors.getAllErrors().size());
    }
}
