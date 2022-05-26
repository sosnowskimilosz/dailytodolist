package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    ProjectStatus status=ProjectStatus.SUBMITTED;
    Long projectLogoId;
    @ManyToOne
    ProjectOwner owner;
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

    public Project(String name, ProjectOwner owner) {
        this.name = name;
        this.owner = owner;
    }
}
