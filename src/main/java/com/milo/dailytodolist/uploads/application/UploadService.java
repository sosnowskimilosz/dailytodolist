package com.milo.dailytodolist.uploads.application;

import com.milo.dailytodolist.uploads.application.port.UploadUseCase;
import com.milo.dailytodolist.uploads.db.UploadJpaRepository;
import com.milo.dailytodolist.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UploadService implements UploadUseCase {

    private final UploadJpaRepository uploadRepository;

    @Override
    public Upload save(SaveUploadCommand command) {
        Upload newUpload = new Upload(command.getFile(), command.getFileName(), command.getContentType());
        uploadRepository.save(newUpload);
        return newUpload;
    }

    @Override
    public void removeById(Long id) {
        uploadRepository.deleteById(id);
    }

    @Override
    public Optional<Upload> getById(Long id) {
        return uploadRepository.findById(id);
    }
}
