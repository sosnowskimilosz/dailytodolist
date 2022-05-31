package com.milo.dailytodolist.owner.web;

import com.milo.dailytodolist.CreatedURI;
import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase;
import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase.CreateProjectOwnerCommand;
import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase.UpdateProjectOwnerCommand;
import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase.UpdateProjectOwnerResponse;
import com.milo.dailytodolist.owner.domain.ProjectOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
public class ProjectOwnerController {

    private final ProjectOwnerUseCase ownerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public List<ProjectOwner> findAll(){
        return ownerService.getAll();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProjectOwner> findById(@PathVariable Long id){
        return ownerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/login")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProjectOwner> findByLogin(@RequestParam String login){
        return ownerService.getByLogin(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // admin and logged owner user
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        ownerService.removeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createProjectOwner(@Valid @RequestBody ProjectOwnerController.ProjectOwnerRestCommand command){
        ProjectOwner newOwner = ownerService.createProjectOwner(command.toCreateProjectOwnerCommand());
        URI uri = createPlayerUri(newOwner);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_ADMIN")
    public void updateProjectOwner(@PathVariable Long id,@RequestBody ProjectOwnerRestCommand command){
        UpdateProjectOwnerResponse response = ownerService.updateProjectOwner(command.toUpdateProjectOwnerCommand(id));
        if(!response.isSuccess()){
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    private URI createPlayerUri(ProjectOwner owner) {
        return new CreatedURI("/" + owner.getId().toString()).uri();
    }

    @Data
    private static class ProjectOwnerRestCommand {

        @NotBlank(message = "Login can not be empty")
        String name;
        @NotBlank(message = "Password can not be empty")
        String password;

        CreateProjectOwnerCommand toCreateProjectOwnerCommand(){
            return new CreateProjectOwnerCommand(name, password);
        }

        UpdateProjectOwnerCommand toUpdateProjectOwnerCommand(Long id){
            return new UpdateProjectOwnerCommand(id, name, password);
        }
    }
}
