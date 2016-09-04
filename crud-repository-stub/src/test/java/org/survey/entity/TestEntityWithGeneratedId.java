package org.survey.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TestEntityWithGeneratedId {
    @Id
    @GeneratedValue
    private Long id;
}