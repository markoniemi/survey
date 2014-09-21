package org.survey.poll.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
public class TextQuestion extends Question {
//    @Transient
    @Column(name="TYPE", insertable=false,updatable=false)
    protected String type = QuestionType.TEXT.name();
}
