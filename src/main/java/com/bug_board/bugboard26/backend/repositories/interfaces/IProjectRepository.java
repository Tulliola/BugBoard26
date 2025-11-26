package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;

import java.util.List;

public interface IProjectRepository {
    public List<Project> getOverviewedProjectsByUser(String username);
    public List<Project> getWorkingOnProjectsByUser(String username);
    public Project save(Project project);
    public List<User> getAddableUsersToProject(Integer idProject);
    public Project getProjectByID(Integer idProject);
    public List<User> getProjectMembers(Integer idProject);
}
