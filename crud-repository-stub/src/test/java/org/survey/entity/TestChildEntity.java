package org.survey.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BOOLEAN")
public class TestChildEntity extends TestEntity {
    public TestChildEntity(String username) {
        super(username);
    }
}