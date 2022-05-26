package com.milo.dailytodolist.project.application;

import com.milo.dailytodolist.owner.db.ProjectOwnerJpaRepository;
import com.milo.dailytodolist.owner.domain.ProjectOwner;
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

    private final ProjectJpaRepository projectRepository;
    private final ProjectOwnerJpaRepository ownerRepository;
    private final UploadUseCase uploadService;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findAll()
                .stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void removeById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project addProject(CreateProjectCommand command) {
        Project project = toProject(command);
        return projectRepository.save(project);
    }

    private Project toProject(CreateProjectCommand command){
        ProjectOwner owner = ownerRepository
                .findById(command.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("There is no owner with id: " + command.getOwnerId()));
        return new Project(command.getName(), owner);
    }


    @Override
    public UpdateProjectResponse updateProject(UpdateProjectCommand command) {
        return projectRepository.findById(command.getId())
                .map(project -> {
                    Project projectToUpdate = command.updateFields(project);
                    projectRepository.save(projectToUpdate);
                    return UpdateProjectResponse.SUCCESS;
                }).orElseGet(() -> new UpdateProjectResponse(
                        false, Collections.singletonList("Project with id: " + command.getId() + " not found")
                ));
    }

    @Override
    public void updateProjectLogo(UpdateProjectLogoCommand command) {
        projectRepository.findById(command.getId())
                .ifPresent(project -> {
                    Upload newUpload = uploadService.save(new UploadUseCase.SaveUploadCommand(command.getFile(), command.getFileName(), command.getContentType()));
                    project.setProjectLogoId(newUpload.getId());
                    projectRepository.save(project);
                });
    }

    @Override
    public void removeProjectLogo(Long id) {
        projectRepository.findById(id)
                .ifPresent(project -> {
                    uploadService.removeById(project.getProjectLogoId());
                    project.setProjectLogoId(null);
                    projectRepository.save(project);
                });
    }

    @Override
    public void assignProjectToOwner(Long projectId, String login) {
        projectRepository.findById(projectId)
                .ifPresent(project -> {
                    ProjectOwner owner = ownerRepository.findAll()
                            .stream()
                            .filter(ownerWithLogin -> ownerWithLogin.getName().equals(login))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No user with login" + login));

                    project.setOwner(owner);
                    projectRepository.save(project);
                });
    }

    @Override
    public List<Project> findByOwnerLogin(String login) {
        return projectRepository.findAll()
                .stream()
                .filter(project -> project.getOwner() != null)
                .filter(project -> project.getOwner().getName().equals(login))
                .collect(Collectors.toList());
    }
}
