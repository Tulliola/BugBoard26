package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryJpaAdapter implements IProjectRepository {
    private final IProjectRepositoryJPA repositoryJPA;

    public ProjectRepositoryJpaAdapter(IProjectRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
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
    public void assignCollaboratorToProject(Integer idProject, String collaboratorUsername) {

    }

    @Override
    public List<User> getAddableUsersToProject(Integer idProject) {
        return List.of();
    }
}
