package com.milo.dailytodolist.owner.domain;

import com.milo.dailytodolist.project.domain.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProjectOwner {

    @Id
    @GeneratedValue
    Long id;
    String name;
    String password;
//    Set<Project> projects;

    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime lastModificationAt;

    public ProjectOwner(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
