package org.survey.file.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.survey.file.model.File;
import org.survey.file.model.FileComparator;
import org.survey.file.model.FileFactory;
import org.survey.file.service.FileService;
import org.survey.user.model.User;
import org.survey.user.model.UserFactory;
import org.survey.user.service.UserService;

/**
 * Test UserService. Must use SpringJUnit4ClassRunner to enable spring
 * injection. Loaded Spring configuration is defined by ContextConfiguration.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:file-service-test.xml")
public class FileServiceImplTest {
    protected static int ENTITY_COUNT = 2;
    protected List<File> orginalEntities = new ArrayList<File>();;
    protected List<File> savedEntities = new ArrayList<File>();;
    @Autowired
    @Qualifier("fileService")
    protected FileService entityService;
    protected FileFactory entityFactory;
    protected FileComparator entityComparator = new FileComparator();
    @Autowired
    @Qualifier("userService")
    protected UserService userService;
    private User user;

    @Before
    public void setUp() {
        UserFactory userFactory = new UserFactory();
        user = userFactory.getEntities(1).get(0);
        user = userService.create(user);
        entityFactory = new FileFactory(user);
    }

    @After
    public void tearDown() throws SQLException {
        File[] users = entityService.findAll();
        if (users != null) {
            for (File fileToDelete : users) {
                entityService.delete(fileToDelete.getId());
            }
        }
        userService.delete(user.getUsername());
    }

    @Test
    public void create() {
        orginalEntities = entityFactory.getEntities(ENTITY_COUNT);
        for (int i = 0; i < ENTITY_COUNT; i++) {
            File originalEntity = orginalEntities.get(i);
            File savedEntity = entityService.create(originalEntity);
            savedEntities.add(savedEntity);
            assertEntity(originalEntity, savedEntity);
        }
    }

    @Test
    public void update() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            File foundEntity = entityService.findOne(savedEntities.get(i)
                    .getId());
            File updatedEntity = entityFactory.getUpdatedEntity(foundEntity);
            updatedEntity.setId(foundEntity.getId());
            entityService.update(updatedEntity);
            foundEntity = entityService.findOne(savedEntities.get(i).getId());
            assertEntity(updatedEntity, foundEntity);
        }
    }

    @Test
    public void findAll() {
        create();
        Assert.assertEquals(ENTITY_COUNT, entityService.findAll().length);
    }

    @Test
    public void findOne() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            File savedEntity = savedEntities.get(i);
            File foundEntity = entityService.findOne(savedEntity.getId());
            assertEntity(savedEntities.get(i), foundEntity);
        }
        // TODO how to test a non-existent entity?
    }

    @Test
    public void exists() {
        create();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            File entity = savedEntities.get(i);
            entityService.exists(entity.getId());
        }
        // TODO how to test if exists fails?
        // Assert.assertFalse(entityRepository.exists((ID) new Object()));
    }

    @Test
    public void existsWithNull() {
        // try with non-existent id
        Assert.assertFalse(entityService.exists(123L));
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
            File entity = savedEntities.get(i);
            entityService.delete(entity.getId());
            Assert.assertFalse(entityService.exists(entity.getId()));
        }
        Assert.assertEquals(0, entityService.count());
    }

    @Test
    public void deleteNonexistent() {
        create();
        entityService.delete(0);
    }

    public void assertEntity(File originalEntity, File entity) {
        Assert.assertEquals("originalEntity: " + originalEntity + " entity: "
                + entity, 0, entityComparator.compare(originalEntity, entity));
    }
}
