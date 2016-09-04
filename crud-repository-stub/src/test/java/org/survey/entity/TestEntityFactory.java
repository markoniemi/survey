package org.survey.entity;

import java.util.ArrayList;
import java.util.List;

import org.survey.repository.EntityFactory;

public class TestEntityFactory implements EntityFactory<TestEntity, String> {
    @Override
    public List<TestEntity> getEntities(int count) {
        List<TestEntity> entities = new ArrayList<TestEntity>();
        for (int i = 0; i < count; i++) {
            TestEntity entity = null;
            switch (i % 2) {
            case 0:
                entity = new TestEntity("username" + i);
                break;
            case 1:
                entity = new TestChildEntity("username" + i);
                break;

            default:
                break;
            }
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public TestEntity getUpdatedEntity(TestEntity entity) {
        TestEntity newEntity = null;
        if (entity instanceof TestChildEntity) {
            newEntity = new TestChildEntity(entity.getUsername() + "_updated");
        } else if (entity instanceof TestEntity) {
            newEntity = new TestEntity(entity.getUsername() + "_updated");
        }
        return newEntity;
    }
}
