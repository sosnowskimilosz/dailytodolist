package com.milo.dailytodolist.project.domain;

import com.milo.dailytodolist.jpa.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Task extends BaseEntity {

    private String title;
    private boolean done;

   public Task(String title) {
        this.title = title;
    }
}
