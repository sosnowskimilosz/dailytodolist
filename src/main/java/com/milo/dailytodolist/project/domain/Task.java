package com.milo.dailytodolist.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private boolean done;

   public Task(String title) {
        this.title = title;
    }
}