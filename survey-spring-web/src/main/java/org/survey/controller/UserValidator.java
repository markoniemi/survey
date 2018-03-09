package org.survey.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.survey.model.user.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        log.info("validate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "invalid");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid");
    }
}
