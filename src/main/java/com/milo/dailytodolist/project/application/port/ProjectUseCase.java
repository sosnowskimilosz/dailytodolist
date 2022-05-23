package com.milo.dailytodolist.project.application.port;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public interface ProjectUseCase {

    List<Project> findAll();

    Optional<Project> findById(Long id);

    List<Project> findByStatus(ProjectStatus status);

    void removeById(Long id);

    Project addProject(CreateProjectCommand command);

    UpdateProjectResponse updateProject(UpdateProjectCommand command);


    @Value
    class CreateProjectCommand {

        String name;
        ProjectStatus status;

        public Project toProject(){
            return new Project(name, status);
        }
    }

    @Value
    class UpdateProjectResponse {
        public static UpdateProjectResponse SUCCESS = new UpdateProjectResponse(true, emptyList());

        boolean isSuccess;
        List<String> errors;
    }

    @Value
    class UpdateProjectCommand {
        Long id;
        String name;
        ProjectStatus status;

        public Project updateFields(Project project) {
            if (name != null) {
                project.setName(name);
            }
            if(status!=null){
                project.setStatus(status);
            }
            return project;
        }
    }
}