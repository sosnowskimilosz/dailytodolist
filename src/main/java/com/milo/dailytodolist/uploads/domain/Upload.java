package com.milo.dailytodolist.uploads.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Upload {

    @Id
    @GeneratedValue
    Long id;
    byte[] file;
    String fileName;
    String contentType;

    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime lastModifiedAt;

    public Upload(byte[] file, String fileName, String contentType) {
        this.file = file;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
