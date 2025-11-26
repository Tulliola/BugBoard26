package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import com.bug_board.bugboard26.exception.backend.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryJpaAdapter implements IProjectRepository {
    private final IProjectRepositoryJPA projectRepositoryJPA;

    public ProjectRepositoryJpaAdapter(IProjectRepositoryJPA projectRepositoryJPA) {
        this.projectRepositoryJPA = projectRepositoryJPA;
    }
    @Override
    public List<Project> getOverviewedProjectsByUser(String username) {
        return List.of();
    }

    @Override
    public List<Project> getWorkingOnProjectsByUser(String username) {
        return List.of();
    }

    @Override
    public Project save(Project project) {
        return this.projectRepositoryJPA.save(project);
    }

    @Override
    public List<User> getAddableUsersToProject(Integer idProject) {
        return List.of();
    }

    @Override
    public Project getProjectByID(Integer idProject) {
        return this.projectRepositoryJPA.findById(idProject).orElse(null);
    }
}
