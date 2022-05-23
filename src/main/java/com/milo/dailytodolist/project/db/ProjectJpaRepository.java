package com.milo.dailytodolist.project.db;

import com.milo.dailytodolist.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project,Long> {
}
