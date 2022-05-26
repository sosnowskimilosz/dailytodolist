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

    void updateProjectLogo(UpdateProjectLogoCommand command);

    void removeProjectLogo(Long id);

    void assignProjectToOwner(Long projectId, String loginOfOwner);

    List<Project> findByOwnerLogin(String login);

    @Value
    class UpdateProjectLogoCommand{
        Long id;
        byte[] file;
        String fileName;
        String contentType;
    }

    @Value
    class CreateProjectCommand {

        String name;
        Long ownerId;
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
        Long authorId;
        ProjectStatus status;

        public Project updateFields(Project project) {
            if (name != null) {
                project.setName(name);
            }
//            if(authorId != null){
//                project.setOwner();
//            }
            if(status!=null){
                project.setStatus(status);
            }
            return project;
        }
    }
}
