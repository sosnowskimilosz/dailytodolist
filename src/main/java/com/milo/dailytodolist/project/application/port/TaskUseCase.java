package com.milo.dailytodolist.project.application.port;

import com.milo.dailytodolist.project.domain.Task;
import lombok.Value;

import java.util.List;

public interface TaskUseCase {

    List<Task> findAllTasksOfProject(Long projectId);

    void removeTaskById(Long projectId, Long taskId);

    List<Task> findTaskByProjectAndStatus(Long projectId, boolean done);

    Task addNewTaskToProject(CreateTaskCommand command);

    UpdateTaskResponse updateTask(UpdateTaskCommand command);

    @Value
    class CreateTaskCommand {
        String title;
        Long projectId;
    }

    @Value
    class UpdateTaskCommand {
        Long id;
        String title;
        boolean done;
    }

    @Value
    class UpdateTaskResponse {
        public static UpdateTaskResponse SUCCESS = new UpdateTaskResponse(false, List.of());

        boolean isSuccess;
        List<String> errors;
    }
}
