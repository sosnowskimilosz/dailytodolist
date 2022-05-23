package com.milo.dailytodolist.uploads.application.port;

import com.milo.dailytodolist.uploads.domain.Upload;
import lombok.Value;
import org.hibernate.sql.Update;

import java.util.Optional;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    void removeById(Long id);

    Optional<Upload> getById(Long id);

    @Value
    class SaveUploadCommand {
        byte[] file;
        String fileName;
        String contentType;

        public Upload toUpdate(){
            return new Upload(file,fileName,contentType);
        }
    }
}
