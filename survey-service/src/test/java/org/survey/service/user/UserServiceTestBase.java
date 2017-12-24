package org.survey.service.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.ServiceConfig;
import org.survey.ServiceTestConfig;
import org.survey.model.user.User;
import org.survey.model.user.UserComparator;
import org.survey.model.user.UserFactory;

/**
 * Test UserService. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = ServiceTestConfig.class))
public class UserServiceTestBase {
    protected static int ENTITY_COUNT = 2;
    protected List<User> orginalEntities = new ArrayList<User>();
    protected List<User> savedEntities = new ArrayList<User>();
    @Resource(name="userService")
    protected UserService entityService;
    protected UserFactory entityFactory = new UserFactory();
    protected UserComparator entityComparator = new UserComparator();

    @After
    public void tearDown() throws SQLException {
        User[] users = entityService.findAll();
        if (users != null) {
            for (User user : users) {
                entityService.delete(user.getUsername());
            }
        }
    }

    @Test
    public void create() {
        orginalEntities = entityFactory.getEntities(ENTITY_COUNT);
        for (int i = 0; i < ENTITY_COUNT; i++) {
            User originalEntity = orginalEntities.get(i);
            User savedEntity = entityService.create(originalEntity);
            savedEntities.add(savedEntity);
            assertEntity(originalEntity, savedEntity);
        }
    }

    @Test
    public void update() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            User foundEntity = entityService.findOne(savedEntities.get(i).getUsername());
            User updatedEntity = entityFactory.getUpdatedEntity(foundEntity);
            updatedEntity.setId(foundEntity.getId());
            entityService.update(updatedEntity);
            foundEntity = entityService.findOne(savedEntities.get(i).getUsername());
            assertEntity(updatedEntity, foundEntity);
        }
    }

    @Test
    public void findAll() {
//        Assert.assertNotNull(entityService.findAll());
        create();
        Assert.assertEquals(ENTITY_COUNT, entityService.findAll().length);
    }

    @Test
    public void findOne() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            User originalEntity = orginalEntities.get(i);
            User foundEntity = entityService.findOne(originalEntity.getUsername());
            assertEntity(orginalEntities.get(i), foundEntity);
        }
        // TODO how to test a non-existent entity?
    }

    @Test
    public void exists() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            User entity = orginalEntities.get(i);
            entityService.exists(entity.getUsername());
        }
        // TODO how to test if exists fails?
        // Assert.assertFalse(entityRepository.exists((ID) new Object()));
    }

    @Test
    public void existsWithNull() {
        // try with non-existent id
        Assert.assertFalse(entityService.exists("nonexistent"));
    }

    @Test
    public void count() {
        create();
        Assert.assertEquals(ENTITY_COUNT, entityService.count());
    }

    @Test
    public void delete() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            User entity = orginalEntities.get(i);
            entityService.delete(entity.getUsername());
            Assert.assertFalse(entityService.exists(entity.getUsername()));
        }
        Assert.assertEquals(0, entityService.count());
    }

    @Test
    public void deleteNonexistent() {
        create();
        entityService.delete("nonexistent");
    }

    public void assertEntity(User originalEntity, User entity) {
        Assert.assertEquals("originalEntity: " + originalEntity + " entity: " + entity, 0,
                entityComparator.compare(originalEntity, entity));
    }
}
