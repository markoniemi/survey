package org.survey.poll.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@ToString(exclude="poll")
@EqualsAndHashCode(of = "id")
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING)
@Table(name = "question")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NonNull
    private String text;
    private String description;
//    @Transient
    @Column(name="TYPE", insertable=false,updatable=false)
    protected String type = QuestionType.LABEL.name();
    
    /**
     * Poll that owns this question.
     */
    @ManyToOne
    Poll poll;
}
