package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.jpa.BaseEntity;
import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project extends BaseEntity {

    String name;
    @Enumerated(EnumType.STRING)
    ProjectStatus status = ProjectStatus.SUBMITTED;
    Long projectLogoId;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    ProjectOwner owner;

    //    LocalDateTime startDate;
//    LocalDateTime deliveryDate;
    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime lastModification;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Task> tasks = new HashSet<>();

    public Project(String name, ProjectOwner owner) {
        this.name = name;
        this.owner = owner;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
