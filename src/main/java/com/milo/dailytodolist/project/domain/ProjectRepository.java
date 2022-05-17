package com.milo.dailytodolist.project.domain;


import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> getAll();

    Project save(Project project);

    Optional<Project> findById(Long id);

    void removeById(Long id);
}
