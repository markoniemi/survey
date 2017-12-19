package org.survey.model.poll;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@ToString(exclude="poll")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "question")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NonNull
    private String text;
    private String description;
    @Enumerated(EnumType.STRING)
    protected QuestionType type;
    
    /**
     * Poll that owns this question.
     */
    @ManyToOne( 
//            fetch = FetchType.EAGER 
            )
    @JoinColumn(name = "poll_id")
  @XmlTransient
            @JsonIgnore
    Poll poll;
}
