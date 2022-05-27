package com.milo.dailytodolist.project.db;

import com.milo.dailytodolist.project.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<Task,Long> {
}
