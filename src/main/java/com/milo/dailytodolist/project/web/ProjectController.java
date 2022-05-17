package com.milo.dailytodolist.project.web;

import com.milo.dailytodolist.CreatedURI;
import com.milo.dailytodolist.project.application.port.ProjectUseCase;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.CreateProjectCommand;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.UpdateProjectCommand;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectUseCase service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Project> getAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return service.findById(id)
                .map(project->ResponseEntity.ok(project))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        service.removeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProject(@Valid @RequestBody RestProjectCommand command){
        Project project = service.addProject(command.toCreateProjectCommand());
        return ResponseEntity.created(createdProjectUri(project)).build();
    }

    private URI createdProjectUri(Project project){
        return new CreatedURI("/"  + project.getId().toString()).uri();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProject(@PathVariable Long id, @RequestBody RestProjectCommand command){
        ProjectUseCase.UpdateProjectResponse response = service.updateProject(command.toUpdateProjectCommand(id));
        if(!response.isSuccess()){
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }


    @Data
    private static class RestProjectCommand {

        @NotBlank
        String name;

        @NotNull
        ProjectStatus status;

        CreateProjectCommand toCreateProjectCommand(){
            return new CreateProjectCommand(name,status);
        }

        UpdateProjectCommand toUpdateProjectCommand(Long id){
            return new UpdateProjectCommand(id,name,status);
        }

    }
}
