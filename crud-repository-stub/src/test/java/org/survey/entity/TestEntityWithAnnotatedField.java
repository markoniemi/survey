package org.survey.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TestEntityWithAnnotatedField {
    @Id
    private String username;
}