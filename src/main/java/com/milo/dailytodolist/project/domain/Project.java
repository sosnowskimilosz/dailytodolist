package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue
    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    ProjectStatus status;
    Long projectLogoId;
//    ProjectOwner owner;
//    LocalDateTime startDate;
//    LocalDateTime deliveryDate;
    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime lastModification;


    public Project(String name, ProjectStatus status) {
        this.name = name;
        this.status = status;
    }
}
