package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceJPA implements IProjectService {

    private final IProjectRepository projectRepository;

    public ProjectServiceJPA(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectSummaryDTO> getOverviewedProjects(String username, String projectNameToFilter) {
        return List.of();
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter) {
        return List.of();
    }

    @Override
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject, String collaboratorUsername) {
        return null;
    }

    @Override
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject) {
        return List.of();
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return projectRepository.getProjectByID(projectId);
    }
}
