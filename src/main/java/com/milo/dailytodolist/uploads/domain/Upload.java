package com.milo.dailytodolist.uploads.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue
    Long id;
    byte[] file;
    String fileName;
    String contentType;

    public Upload(byte[] file, String fileName, String contentType) {
        this.file = file;
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
