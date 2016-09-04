package org.survey.repository;

import org.junit.Before;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.survey.entity.TestEntity;
import org.survey.entity.TestEntityComparator;
import org.survey.entity.TestEntityFactory;

public class TestEntityRepositoryTest extends CrudRepositoryTest<TestEntity, String> {
    TestEntityRepository entityRepository = new TestEntityRepository();

    @Before
    public void setUp() {
        entityFactory = new TestEntityFactory();
        entityComparator = new TestEntityComparator();
    }

    @Override
    public PagingAndSortingRepository<TestEntity, String> getEntityRepository() {
        return entityRepository;
    }
}
