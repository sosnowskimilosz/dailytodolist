package com.milo.dailytodolist;

import com.milo.dailytodolist.owner.domain.ProjectOwner;
import com.milo.dailytodolist.project.application.ProjectService;
import com.milo.dailytodolist.project.application.port.ProjectUseCase.CreateProjectCommand;
import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupApplication implements CommandLineRunner {

    private final ProjectService service;

    @Override
    public void run(String... args) throws Exception {
        initData();
        for (Project project:service.findAll()) {
            System.out.println(project);
        }
    }

    private void initData() {

//        ProjectOwner milosz = new ProjectOwner(1L,"Milosz");
//        ProjectOwner adam = new ProjectOwner(2L,"Adam");
        Project project1 = service.addProject(new CreateProjectCommand("Zakup mieszkania",  ProjectStatus.STARTED));
        Project project2 = service.addProject(new CreateProjectCommand("Zakupy", ProjectStatus.SUBMITTED));
        Project project3 = service.addProject(new CreateProjectCommand("Zmiana pracy",  ProjectStatus.ON_HOLD));
    }
}
