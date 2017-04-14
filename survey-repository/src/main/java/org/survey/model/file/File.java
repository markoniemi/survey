package org.survey.model.file;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.survey.model.user.User;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "content")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "file")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("PMD.UnusedPrivateField")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String filename;
    @NonNull
    private String mimeType;
    private Long createTime;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
//    @XmlTransient
    private User owner;
    private String url;
    /**
     * content contains the binary data for file. FetchType.LAZY is recommended
     * for blobs.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NonNull
//    @XmlTransient
    private byte[] content;
}
