package org.survey.validator;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import org.junit.Assert;
import org.junit.Test;

public class EmailValidatorTest {

    @Test
    public void validate() {
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(null, null, "email@test.com");
    }

    @Test
    public void validateWithError() {
        try {
            EmailValidator emailValidator = new EmailValidator();
            emailValidator.validate(null, null, "invalid");
            Assert.fail();
        } catch (ValidatorException e) {
            Assert.assertEquals(FacesMessage.SEVERITY_ERROR, e
                    .getFacesMessage().getSeverity());
        }
    }

    @Test
    public void validateWithError2() {
        try {
            EmailValidator emailValidator = new EmailValidator();
            emailValidator.validate(null, null, "invalid@test");
            Assert.fail();
        } catch (ValidatorException e) {
            Assert.assertEquals(FacesMessage.SEVERITY_ERROR, e
                    .getFacesMessage().getSeverity());
        }
    }
}
