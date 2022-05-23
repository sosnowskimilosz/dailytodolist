package com.milo.dailytodolist.uploads.db;

import com.milo.dailytodolist.uploads.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
