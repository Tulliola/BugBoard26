package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.Project;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.dto.UserSummaryDTO;

import java.util.List;

public interface IProjectService {
    public List<ProjectSummaryDTO> getOverviewedProjects(String username, String projectNameToFilter);
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter);
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject, String collaboratorUsername);
    public Project getProject(Integer idProject);
}
