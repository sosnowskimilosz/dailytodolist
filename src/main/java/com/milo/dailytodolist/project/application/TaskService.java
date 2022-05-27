package com.milo.dailytodolist.project.application;

import com.milo.dailytodolist.project.application.port.TaskUseCase;
import com.milo.dailytodolist.project.db.ProjectJpaRepository;
import com.milo.dailytodolist.project.db.TaskJpaRepository;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TaskService implements TaskUseCase {

    TaskJpaRepository taskRepository;
    ProjectJpaRepository projectRepository;

    @Override
    public List<Task> findAllTasksOfProject(Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> new ArrayList<>(project.getTasks()))
                .orElseThrow(() -> new IllegalArgumentException("There is no project with id=" + projectId));
    }

    @Override
    @Transactional
    public void removeTaskById(Long projectId, Long taskId) {
        Project project = findProject(projectId);
        Task taskToRemove = findTask(taskId);
        project.removeTask(taskToRemove);
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> findTaskByProjectAndStatus(Long projectId, boolean done) {
        return projectRepository.findById(projectId)
                .map(project -> project.getTasks()
                        .stream()
                        .filter(task -> task.isDone() == done)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("There is no project with id=" + projectId));
    }

    @Override
    @Transactional
    public Task addNewTaskToProject(CreateTaskCommand command) {
        Project project = findProject(command.getProjectId());
        Task newTask = new Task(command.getTitle());
        project.addTask(newTask);
        return taskRepository.save(newTask);
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No project with id=" + id));
    }

    private Task findTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No task with id=" + id));
    }

    @Override
    public UpdateTaskResponse updateTask(UpdateTaskCommand command) {
        return taskRepository.findById(command.getId())
                .map(task -> {
                    Task taskToUpdate = updateTaskFields(task,command);
                    taskRepository.save(taskToUpdate);
                    return UpdateTaskResponse.SUCCESS;
                }).orElseGet(()-> new UpdateTaskResponse(false,List.of("Task with id: " + command.getId() + " not found")));
    }

    @Override
    @Transactional
    public void changeDone(Long id) {
        Task task = findTask(id);
        task.setDone(!task.isDone());
    }


    private Task updateTaskFields(Task task, UpdateTaskCommand command){
        if(command.getTitle() != null){
            task.setTitle(command.getTitle());
        }
        return task;
    }
}
