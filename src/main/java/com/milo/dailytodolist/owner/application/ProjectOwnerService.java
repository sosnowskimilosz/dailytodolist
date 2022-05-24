package com.milo.dailytodolist.owner.application;

import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase;
import com.milo.dailytodolist.owner.db.ProjectOwnerJpaRepository;
import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectOwnerService implements ProjectOwnerUseCase {

    ProjectOwnerJpaRepository repository;

    @Override
    public List<ProjectOwner> getAll() {
       return repository.findAll();
    }

    @Override
    public Optional<ProjectOwner> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ProjectOwner> getByLogin(String login) {
        return repository.findAll()
                .stream().filter(projectOwner -> projectOwner.getName().equals(login)).findFirst();
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProjectOwner createProjectOwner(CreateProjectOwnerCommand command) {
        ProjectOwner newProjectOwner = command.toProjectOwner();
        return repository.save(newProjectOwner);
     }

    @Override
    public UpdateProjectOwnerResponse updateProjectOwner(UpdateProjectOwnerCommand command) {
        return repository.findById(command.getId())
                .map(projectOwner -> {
                    ProjectOwner updatedProjectOwner = command.updateFields(projectOwner);
                    repository.save(updatedProjectOwner);
                    return UpdateProjectOwnerResponse.SUCCESS;
                }).orElse(new UpdateProjectOwnerResponse(false, List.of("ProjectOwner with id" + command.getId() + "not found")));
    }
}
