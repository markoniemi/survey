package org.survey.model.poll;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BOOLEAN")
public class BooleanQuestion extends Question {
//    @Transient
    @Column(name="TYPE", insertable=false,updatable=false)
    protected String type = QuestionType.BOOLEAN.name();
}
