package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceJPA implements IProjectService {
    @Override
    public List<ProjectSummaryDTO> getOverviewedProjects(String username, String projectNameToFilter) {
        return List.of();
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter) {
        return List.of();
    }

    @Override
    public void assignCollaboratorToProject(Integer idProject, String collaboratorUsername) {

    }

    @Override
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject) {
        return List.of();
    }
}
