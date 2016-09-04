package org.survey.entity;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.survey.repository.EntityComparator;

public class TestChildEntityComparator extends EntityComparator<TestChildEntity, String> {
    @Override
    public int compare(TestChildEntity entity1, TestChildEntity entity2) {
        if (entity1 == entity2) {
            return 0;
        }
        if (entity1 == null) {
            return -1;
        }
        if (entity2 == null) {
            return 1;
        }
        return new CompareToBuilder().append(entity1.getUsername(), entity2.getUsername()).toComparison();
    }
}
