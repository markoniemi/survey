package org.survey.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntityWithAnnotatedGetter {
    private String username;

    @Id
    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    public String getUsername() {
        return username;
    }
}