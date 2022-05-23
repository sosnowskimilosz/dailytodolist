package com.milo.dailytodolist.project.application;

import com.milo.dailytodolist.project.application.port.ProjectUseCase;
import com.milo.dailytodolist.project.db.ProjectJpaRepository;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import com.milo.dailytodolist.uploads.application.port.UploadUseCase;
import com.milo.dailytodolist.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements ProjectUseCase {

    private final ProjectJpaRepository repository;
    private final UploadUseCase uploadService;

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) {
        return repository.findAll()
                .stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Project addProject(CreateProjectCommand command) {
        Project project = command.toProject();
        return repository.save(project);
    }

    @Override
    public UpdateProjectResponse updateProject(UpdateProjectCommand command) {
        return repository.findById(command.getId())
                .map(project -> {
                    Project projectToUpdate = command.updateFields(project);
                    repository.save(projectToUpdate);
                    return UpdateProjectResponse.SUCCESS;
                }).orElseGet(() -> new UpdateProjectResponse(
                        false, Collections.singletonList("Project with id: " + command.getId() + " not found")
                ));
    }

    @Override
    public void updateProjectLogo(UpdateProjectLogoCommand command) {
        repository.findById(command.getId())
                .ifPresent(project -> {
                    Upload newUpload = uploadService.save(new UploadUseCase.SaveUploadCommand(command.getFile(), command.getFileName(), command.getContentType()));
                    project.setProjectLogoId(newUpload.getId());
                    repository.save(project);
                });
    }

    @Override
    public void removeProjectLogo(Long id) {
        repository.findById(id)
                .ifPresent(project -> {
                    uploadService.removeById(project.getProjectLogoId());
                    project.setProjectLogoId(null);
                    repository.save(project);
                });
    }
}
