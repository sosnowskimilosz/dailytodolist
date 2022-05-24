package com.milo.dailytodolist.owner.db;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectOwnerJpaRepository extends JpaRepository<ProjectOwner,Long> {
}
