package com.milo.dailytodolist.project.application.port;

import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public interface ProjectUseCase {

    List<Project> findAllProjects();

    Optional<Project> findProjectById(Long id);

    List<Project> findProjectByStatus(ProjectStatus status);

    void removeProjectById(Long id);

    Project addProject(CreateProjectCommand command);

    UpdateProjectResponse updateProject(UpdateProjectCommand command);

    void updateProjectLogo(UpdateProjectLogoCommand command);

    void removeProjectLogo(Long id);

    void changeProjectOwner(Long projectId, String loginOfOwner);

    List<Project> findProjectByOwnerLogin(String login);

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
        Long ownerId;
        ProjectStatus status;
    }
}
