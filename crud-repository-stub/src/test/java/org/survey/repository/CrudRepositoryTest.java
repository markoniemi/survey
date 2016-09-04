package org.survey.repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.collections.IteratorUtils;
import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class CrudRepositoryTest<T, ID extends Serializable> {
    protected static int ENTITY_COUNT = 5;
    protected List<T> orginalEntities = new ArrayList<T>();;
    protected List<T> savedEntities = new ArrayList<T>();;
    protected PagingAndSortingRepository<T, ID> entityRepository;
    protected EntityFactory<T, ID> entityFactory;
    protected EntityComparator<T, ID> entityComparator;

    public abstract PagingAndSortingRepository<T, ID> getEntityRepository();

    @After
    public void tearDown() throws DatabaseUnitException, SQLException {
        getEntityRepository().deleteAll();
    }

    @Test
    public void save() {
        orginalEntities = entityFactory.getEntities(ENTITY_COUNT);
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T originalEntity = orginalEntities.get(i);
            T savedEntity = getEntityRepository().save(originalEntity);
            savedEntities.add(savedEntity);
            assertEntity(originalEntity, savedEntity);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void save2() {
        orginalEntities = entityFactory.getEntities(ENTITY_COUNT);
        List<T> entitiesToSave = new ArrayList<T>();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T originalEntity = orginalEntities.get(i);
            entitiesToSave.add(originalEntity);
        }
        List<T> savedEntities = IteratorUtils.toList(getEntityRepository().save(entitiesToSave).iterator());
        Assert.assertEquals(ENTITY_COUNT, savedEntities.size());
        for (int i = 0; i < ENTITY_COUNT; i++) {
            assertEntity(orginalEntities.get(i), savedEntities.get(i));
            T foundEntity = getEntityRepository().findOne((ID) BeanHelper.getId(savedEntities.get(i)));
            assertEntity(orginalEntities.get(i), foundEntity);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void update() {
        save();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T foundEntity = getEntityRepository().findOne((ID) BeanHelper.getId(savedEntities.get(i)));
            T updatedEntity = entityFactory.getUpdatedEntity(foundEntity);
            BeanHelper.setValueOfAnnotatedField(updatedEntity, Id.class, (ID) BeanHelper.getId(foundEntity));
            getEntityRepository().save(updatedEntity);
            foundEntity = getEntityRepository().findOne((ID) BeanHelper.getId(savedEntities.get(i)));
            assertEntity(updatedEntity, foundEntity);
        }
    }

    @Test
    public void findAll() {
        save();
        @SuppressWarnings("unchecked")
        List<T> entities = IteratorUtils.toList(getEntityRepository().findAll().iterator());
        Assert.assertEquals(ENTITY_COUNT, entities.size());
    }
    @Ignore
    @Test
    public void findAllWithSort() {
        save();
        // how to get property names
        Sort sort = new Sort("property");
        @SuppressWarnings("unchecked")
        List<T> entities = IteratorUtils.toList(getEntityRepository().findAll(sort).iterator());
        Assert.assertEquals(ENTITY_COUNT, entities.size());
    }
    @Test
    public void findAllWithPageable() {
        save();
        PageRequest pageRequest = new PageRequest(0, ENTITY_COUNT);
        @SuppressWarnings("unchecked")
        List<T> entities = IteratorUtils.toList(getEntityRepository().findAll(pageRequest).iterator());
        Assert.assertEquals(ENTITY_COUNT, entities.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOne() {
        save();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T originalEntity = orginalEntities.get(i);
            T foundEntity = getEntityRepository().findOne((ID) BeanHelper.getId(originalEntity));
            assertEntity(orginalEntities.get(i), foundEntity);
        }
        // TODO how to test a non-existent entity?
    }

    @SuppressWarnings("unchecked")
    @Test
    public void exists() {
        save();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T entity = orginalEntities.get(i);
            getEntityRepository().exists((ID) BeanHelper.getId(entity));
        }
        // TODO how to test if exists fails?
        // Assert.assertFalse(entityRepository.exists((ID) new Object()));
    }

//    @Test(expected=IllegalArgumentException.class)
    @Test(expected=InvalidDataAccessApiUsageException.class)
    public void existsWithNull() {
        Assert.assertFalse(getEntityRepository().exists(null));
    }

    @Test
    public void count() {
        save();
        Assert.assertEquals(ENTITY_COUNT, getEntityRepository().count());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void delete() {
        save();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T entity = orginalEntities.get(i);
            getEntityRepository().delete((ID) BeanHelper.getId(entity));
            Assert.assertFalse(getEntityRepository().exists((ID) BeanHelper.getId(entity)));
        }
        Assert.assertEquals(0, getEntityRepository().count());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void delete2() {
        save();
        List<T> entitiesToDelete = new ArrayList<T>();
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T originalEntity = orginalEntities.get(i);
            entitiesToDelete.add(originalEntity);
        }
        getEntityRepository().delete(entitiesToDelete);
        Assert.assertEquals(0, getEntityRepository().count());
        for (int i = 0; i < ENTITY_COUNT; i++) {
            T entity = orginalEntities.get(i);
            Assert.assertFalse(getEntityRepository().exists((ID) BeanHelper.getId(entity)));
        }
    }

    @Test
    public void deleteAll() {
        save();
        getEntityRepository().deleteAll();
        @SuppressWarnings("unchecked")
        List<T> entities = IteratorUtils.toList(getEntityRepository().findAll().iterator());
        Assert.assertEquals(0, entities.size());
    }

    public void assertEntity(T originalEntity, T entity) {
        Assert.assertEquals("originalEntity: " + originalEntity + " entity: " + entity, 0,
                entityComparator.compare(originalEntity, entity));
    }
}
