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
import java.util.HashSet;
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

    public Project(String name, ProjectStatus status) {
        this.name = name;
        this.status = status;
    }

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
