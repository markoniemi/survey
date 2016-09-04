package org.survey.repository;

import java.lang.reflect.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.junit.Assert;
import org.junit.Test;
import org.survey.entity.TestEntityWithAnnotatedField;
import org.survey.entity.TestEntityWithAnnotatedGetter;
import org.survey.entity.TestEntityWithGeneratedId;

public class BeanHelperTest {

    @Test
    public void fieldToGetter() {
        Assert.assertEquals("getUsername", BeanHelper.fieldToGetter("username"));
    }

    @Test
    public void fieldToSetter() {
        Assert.assertEquals("setUsername", BeanHelper.fieldToSetter("username"));
    }

    @Test
    public void getGetterMethodWithAnnotation() throws Exception {
        Method method = BeanHelper.getGetterMethodWithAnnotation(TestEntityWithAnnotatedGetter.class, Id.class);
        Assert.assertEquals("getUsername", method.getName());
    }

    @Test
    public void getSetterMethodWithAnnotation() throws Exception {
        Method method = BeanHelper.getSetterMethodWithAnnotation(TestEntityWithAnnotatedGetter.class, Id.class);
        Assert.assertEquals("setUsername", method.getName());
    }

    @Test
    public void getGetterMethodForFieldWithAnnotation() throws Exception {
        Method method = BeanHelper.getGetterMethodForFieldWithAnnotation(TestEntityWithAnnotatedField.class, Id.class);
        Assert.assertEquals("getUsername", method.getName());
    }

    @Test
    public void getSetterMethodForFieldWithAnnotation() throws Exception {
        Method method = BeanHelper.getSetterMethodForFieldWithAnnotation(TestEntityWithAnnotatedField.class, Id.class,
                String.class);
        Assert.assertEquals("setUsername", method.getName());
    }

    @Test
    public void getValueOfAnnotatedFieldWithAnnotatedField() {
        TestEntityWithAnnotatedField testEntity = new TestEntityWithAnnotatedField();
        testEntity.setUsername("username1");
        Assert.assertEquals("username1", BeanHelper.getValueOfAnnotatedField(testEntity, Id.class));
    }

    @Test
    public void setValueOfAnnotatedFieldWithAnnotatedField() {
        TestEntityWithAnnotatedField testEntity = new TestEntityWithAnnotatedField();
        BeanHelper.setValueOfAnnotatedField(testEntity, Id.class, "username1");
        Assert.assertEquals("username1", testEntity.getUsername());
    }

    @Test
    public void getValueOfAnnotatedFieldWithAnnotatedGetter() {
        TestEntityWithAnnotatedGetter testEntity = new TestEntityWithAnnotatedGetter();
        testEntity.setUsername("username1");
        Assert.assertEquals("username1", BeanHelper.getValueOfAnnotatedField(testEntity, Id.class));

    }

    @Test
    public void setValueOfAnnotatedFieldWithAnnotatedGetter() {
        TestEntityWithAnnotatedGetter testEntity = new TestEntityWithAnnotatedGetter();
        BeanHelper.setValueOfAnnotatedField(testEntity, Id.class, "username1");
        Assert.assertEquals("username1", testEntity.getUsername());
    }

    // TODO re-write using test entity
    // @Test
    // public void getValueOfAnnotatedField() {
    // User user = new User("username1", "password", "email", Role.ROLE_ADMIN);
    // Assert.assertEquals("username1",
    // BeanHelper.getValueOfAnnotatedField(user, Id.class));
    // user = new User("username2", "password", "email", Role.ROLE_ADMIN);
    // Assert.assertEquals("username2",
    // BeanHelper.getValueOfAnnotatedField(user, Id.class));
    // }
    @Test
    public void setValueOfAnnotatedFieldWithGeneratedValueAnnotation() {
        TestEntityWithGeneratedId testClass = new TestEntityWithGeneratedId();
        BeanHelper.setValueOfAnnotatedField(testClass, GeneratedValue.class, Long.valueOf(1));
        Assert.assertEquals(Long.valueOf(1), testClass.getId());
    }
}
