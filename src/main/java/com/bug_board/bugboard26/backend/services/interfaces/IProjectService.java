package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;

import java.util.List;

public interface IProjectService {
    public List<ProjectSummaryDTO> getOverviewedProjects(String username, String projectNameToFilter);
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter);
    public void assignCollaboratorToProject(Integer idProject, String collaboratorUsername);
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject);
}
