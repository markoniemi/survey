package org.survey.file.model;

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

import org.survey.user.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
    private Long size;
    @ManyToOne(optional = true)
    @JoinColumn(name = "username")
    private User owner;
    private String url;
    /**
     * content contains the binary data for file. FetchType.LAZY is recommended
     * for blobs.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NonNull
    private byte[] content;
}
