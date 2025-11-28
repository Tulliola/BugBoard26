package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.Project;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
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
        return projectRepositoryJPA.findAllByAdmins_Username(username);
    }

    @Override
    public List<Project> getWorkingOnProjectsByUser(String username) {
        return projectRepositoryJPA.findAllByPartecipants_Username(username);
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

    @Override
    public List<User> getProjectMembers(Integer idProject) {
        return projectRepositoryJPA.getProjectMembers(idProject);
    }

    @Override
    public List<Project> getOverviewedProjectsByUserWithName(String username, String projectNameToFilter) {
        return projectRepositoryJPA.findAllByTitleAndAdmins_Username(username, projectNameToFilter);
    }

    @Override
    public List<Project> getWorkingOnProjectsByUserWithName(String username, String projectNameToFilter) {
        return projectRepositoryJPA.findAllByTitleAndPartecipants_Username(username, projectNameToFilter);
    }
}
