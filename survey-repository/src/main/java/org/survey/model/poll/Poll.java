package org.survey.model.poll;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.survey.model.user.User;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = { "id", "name" })
@Entity
@Table(name = "poll")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User owner;
    /**
     * <p>
     * If you have a bidirectional ManyToMany relationship, you must use
     * mappedBy on one side of the relationship, otherwise it will be assumed to
     * be two difference relationships and you will get duplicate rows inserted
     * into the join table.
     * <p>
     * <a href=
     * "http://en.wikibooks.org/wiki/Java_Persistence/ManyToMany#Duplicate_rows_inserted_into_the_join_table"
     * />
     * <p>
     * Apparently the Spring JPA does not work with cascades.
     * </p>
     * <a href=
     * "http://stackoverflow.com/questions/16326916/spring-data-jpa-causes-entityexistsexception-with-cascading-save"
     * />
     */
    @OneToMany(fetch = FetchType.EAGER, 
            mappedBy = "poll", 
            orphanRemoval=true, 
            cascade = {
     CascadeType.ALL,
//     CascadeType.PERSIST,
//     CascadeType.MERGE,
//     CascadeType.REMOVE,
//     CascadeType.REFRESH,
//     CascadeType.DETACH
    })
//     @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//     @Cascade({org.hibernate.annotations.CascadeType.ALL})
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @XmlTransient
    // TODO init questions
    private List<Question> questions;
}
