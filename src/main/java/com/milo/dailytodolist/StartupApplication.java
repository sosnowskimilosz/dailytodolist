package com.milo.dailytodolist;

import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase;
import com.milo.dailytodolist.owner.application.port.ProjectOwnerUseCase.CreateProjectOwnerCommand;
import com.milo.dailytodolist.owner.domain.ProjectOwner;
import com.milo.dailytodolist.project.application.ProjectService;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.CreateProjectCommand;
import com.milo.dailytodolist.project.application.port.TaskUseCase;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.Task;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class StartupApplication implements CommandLineRunner {

    private final ProjectService projectService;
    private final ProjectOwnerUseCase projectOwnerService;
    private final TaskUseCase taskService;

    @Override
    public void run(String... args) {
        initData();
        for (Project project : projectService.findAllProjects()) {
            System.out.println(project);
        }
        for (ProjectOwner owner : projectOwnerService.getAll()) {
            System.out.println(owner);
        }
    }

    private void initData() {
        ProjectOwner admin = projectOwnerService.createProjectOwner(new CreateProjectOwnerCommand("admin", "123"));
        ProjectOwner user = projectOwnerService.createProjectOwner(new CreateProjectOwnerCommand("user", "123"));
        ProjectOwner marian = projectOwnerService.createProjectOwner(new CreateProjectOwnerCommand("Marian", "pass123"));

        Project project1 = projectService.addProject(new CreateProjectCommand("Zakup mieszkania", admin.getId()));
        Project project2 = projectService.addProject(new CreateProjectCommand("Zakupy", admin.getId()));
        Project project3 = projectService.addProject(new CreateProjectCommand("Zmiana pracy", user.getId()));
        Project project4 = projectService.addProject(new CreateProjectCommand("Nauka C#", admin.getId()));

        Task task1 = taskService.addNewTaskToProject(new TaskUseCase.CreateTaskCommand("uzbieranie mamony", project1.getId()));
        Task task2 = taskService.addNewTaskToProject(new TaskUseCase.CreateTaskCommand("wybor mieszkania", project1.getId()));
        Task task3 = taskService.addNewTaskToProject(new TaskUseCase.CreateTaskCommand("podpisanie umowy przedwstepnej", project1.getId()));
        Task task4 = taskService.addNewTaskToProject(new TaskUseCase.CreateTaskCommand("wizyta u notariusza", project1.getId()));
        taskService.removeTaskById(project1.getId(), task1.getId());
    }
}
