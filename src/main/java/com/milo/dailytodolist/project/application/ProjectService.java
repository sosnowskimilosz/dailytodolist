package com.milo.dailytodolist.project.application;

import com.milo.dailytodolist.project.application.port.ProjectUseCase;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectRepository;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService implements ProjectUseCase {

    private final ProjectRepository repository;

    @Override
    public List<Project> findAll() {
        return repository.getAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) {
        return repository.getAll()
                .stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
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
}
