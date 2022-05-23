package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    Long id;
    String name;
    ProjectStatus status;
//    LocalDateTime startDate;
//    LocalDateTime deliveryDate;
//    LocalDateTime createdDate;
//    LocalDateTime lastModificationDate;


    public Project(String name, ProjectStatus status) {
        this.name = name;
        this.status = status;
    }
}
