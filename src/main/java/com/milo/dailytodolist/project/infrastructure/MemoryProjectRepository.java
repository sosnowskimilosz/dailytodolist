package com.milo.dailytodolist.project.infrastructure;

import com.milo.dailytodolist.project.domain.Project;
import com.milo.dailytodolist.project.domain.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryProjectRepository  implements ProjectRepository {

    private final Map<Long, Project> storage = new HashMap<>();
    private final AtomicLong NEXT_ID=new AtomicLong(0);

    @Override
    public List<Project> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Project save(Project project) {
        if(project.getId()!=null){
            storage.put(project.getId(), project);
        }else{
            Long nextId=nextId();
            project.setId(nextId);
            storage.put(nextId,project);
        }
        return project;
    }

    Long nextId(){
        return NEXT_ID.getAndIncrement();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void removeById(Long id) {
        storage.remove(id);
    }
}
