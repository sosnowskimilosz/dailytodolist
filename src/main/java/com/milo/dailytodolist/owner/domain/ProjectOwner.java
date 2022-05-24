package com.milo.dailytodolist.owner.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class ProjectOwner {

    @Id
    @GeneratedValue
    Long id;
    String name;
    String password;

    public ProjectOwner(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
