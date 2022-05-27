package com.milo.dailytodolist.owner.domain;

import com.milo.dailytodolist.jpa.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProjectOwner extends BaseEntity {

    String name;
    String password;

    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime lastModificationAt;

    public ProjectOwner(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
