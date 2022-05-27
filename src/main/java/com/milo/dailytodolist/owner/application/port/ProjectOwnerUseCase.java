package com.milo.dailytodolist.owner.application.port;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface ProjectOwnerUseCase {

    List<ProjectOwner> getAll();

    Optional<ProjectOwner> getById(Long id);

    Optional<ProjectOwner> getByLogin(String login);

    void removeById(Long id);

    ProjectOwner createProjectOwner(CreateProjectOwnerCommand command);

    UpdateProjectOwnerResponse updateProjectOwner(UpdateProjectOwnerCommand command);

    @Value
    class CreateProjectOwnerCommand {
        String name;
        String password;

        public ProjectOwner toProjectOwner(){
            return new ProjectOwner(name,password);
        }
    }

    @Value
    class UpdateProjectOwnerCommand {
        Long id;
        String name;
        String password;

        public ProjectOwner updateFields(ProjectOwner owner){
            if (name!=null){
                owner.setName(name);
            }
            if (password!=null){
                owner.setPassword(password);
            }
            return owner;
        }
    }

    @Value
    class UpdateProjectOwnerResponse {
        boolean success;
        List<String> errors;

        public static UpdateProjectOwnerResponse SUCCESS = new UpdateProjectOwnerResponse(true, List.of());
    }



}
