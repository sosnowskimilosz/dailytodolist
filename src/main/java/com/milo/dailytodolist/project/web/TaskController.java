package com.milo.dailytodolist.project.web;

import com.milo.dailytodolist.CreatedURI;
import com.milo.dailytodolist.project.application.port.ProjectUseCase;
import com.milo.dailytodolist.project.application.port.TaskUseCase;
import com.milo.dailytodolist.project.application.port.TaskUseCase.CreateTaskCommand;
import com.milo.dailytodolist.project.application.port.TaskUseCase.UpdateTaskCommand;
import com.milo.dailytodolist.project.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class TaskController {

    private final ProjectUseCase projectService;
    private final TaskUseCase taskService;

    @GetMapping("/{projectId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksOfProject(@PathVariable Long projectId) {
        return taskService.findAllTasksOfProject(projectId);
    }

    @GetMapping("/{projectId}/tasks/status")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByStatus(@PathVariable Long projectId, @RequestParam("isdone") boolean status) {
        return taskService.findTaskByProjectAndStatus(projectId, status);
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.removeTaskById(projectId, taskId);
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Task> addTaskToProject(@PathVariable Long projectId, @Valid @RequestBody RestTaskCommand command) {
        Task newTask = taskService.addNewTaskToProject(command.toCreateCommand(projectId));
        return ResponseEntity.created(createdTaskUri(newTask)).build();
    }

    private URI createdTaskUri(Task task) {
        return new CreatedURI("/" + task.getId().toString()).uri();
    }

    @PutMapping("/{projectId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTask(@PathVariable Long taskId, @RequestBody RestTaskCommand command) {
        taskService.updateTask(command.toUpdateTaskCommand(taskId));
    }

    @PutMapping("/{projectId}/tasks/{taskId}/changestatus")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatus(@PathVariable Long taskId) {
        taskService.changeDone(taskId);
    }

    @Value
    private static class RestTaskCommand {
        @NotNull
        String title;
        boolean done;

        CreateTaskCommand toCreateCommand(Long projectId) {
            return new CreateTaskCommand(title, projectId);
        }

        UpdateTaskCommand toUpdateTaskCommand(Long id) {
            return new UpdateTaskCommand(id, title);
        }
    }
}
