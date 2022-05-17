package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Project {

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
