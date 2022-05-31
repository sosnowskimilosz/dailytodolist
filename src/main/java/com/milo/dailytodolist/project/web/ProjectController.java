package com.milo.dailytodolist.project.web;

import com.milo.dailytodolist.CreatedURI;
import com.milo.dailytodolist.project.application.port.ProjectUseCase;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.CreateProjectCommand;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.UpdateProjectCommand;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import com.milo.dailytodolist.security.UserSecurity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectUseCase projectService;
    private final UserSecurity userSecurity;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<Project> getAll(@AuthenticationPrincipal User user) {
        if (userSecurity.isAdmin(user)) {
            return projectService.findAllProjects();
        } else {
            return projectService.findAllProjects()
                    .stream()
                    .filter(project -> userSecurity.isOwner(project.getOwner().getName(), user))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return projectService.findProjectById(id)
                .map(project -> authorize(project, user))
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Project> authorize(Project project, User user){
        if (userSecurity.isOwnerOrAdmin(project.getOwner().getName(), user)){
            return ResponseEntity.ok(project);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Project project = findProject(id);
        if(userSecurity.isOwnerOrAdmin(project.getOwner().getName(), user)){
            projectService.removeProjectById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private Project findProject(Long id){
        return projectService.findProjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("No project with id="+id));
    }

    //admin and logged owner user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProject(@Valid @RequestBody RestProjectCommand command) {
        Project project = projectService.addProject(command.toCreateProjectCommand());
        return ResponseEntity.created(createdProjectUri(project)).build();
    }

    private URI createdProjectUri(Project project) {
        return new CreatedURI("/" + project.getId().toString()).uri();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProject(@PathVariable Long id, @RequestBody RestProjectCommand command) {
        ProjectUseCase.UpdateProjectResponse response = projectService.updateProject(command.toUpdateProjectCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    //admin and logged owner user
    @PutMapping(value = "/{id}/logo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addLogoToProject(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        projectService.updateProjectLogo(new ProjectUseCase.UpdateProjectLogoCommand(
                id,
                file.getBytes(),
                file.getOriginalFilename(),
                file.getContentType()
        ));
    }

    //admin and logged owner user
    @DeleteMapping("/{id}/logo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProjectLogo(@PathVariable Long id) {
        projectService.removeProjectLogo(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/updateowner")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeProjectOwner(@PathVariable Long id, @RequestParam("login") String login) {
        projectService.changeProjectOwner(id, login);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/byowner")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findProjectsByOwnerLogin(@RequestParam("login") String login) {
        return projectService.findProjectByOwnerLogin(login);
    }

    @Data
    private static class RestProjectCommand {

        @NotBlank
        String name;

        @PositiveOrZero
        Long owner;

        ProjectStatus status;

        CreateProjectCommand toCreateProjectCommand() {
            return new CreateProjectCommand(name, owner);
        }

        UpdateProjectCommand toUpdateProjectCommand(Long id) {
            return new UpdateProjectCommand(id, name, owner, status);
        }
    }
}
