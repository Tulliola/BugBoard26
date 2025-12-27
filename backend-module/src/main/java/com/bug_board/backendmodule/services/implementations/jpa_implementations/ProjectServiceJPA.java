package com.bug_board.backendmodule.services.implementations.jpa_implementations;

import com.bug_board.backendmodule.entity.Project;
import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.repositories.interfaces.IProjectRepository;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.backendmodule.mappers.ProjectMapper;
import com.bug_board.backendmodule.mappers.UserMapper;
import com.bug_board.backendmodule.exception.backend.ResourceAlreadyExistsException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.dto.UserSummaryDTO;
import com.bug_board.dto.ProjectSummaryDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ProjectServiceJPA implements IProjectService {

    private final IProjectRepository projectRepository;

    private final IUserService userService;

    public ProjectServiceJPA(IProjectRepository projectRepository, IUserService userRepository) {
        this.userService = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public List<ProjectSummaryDTO> getOverviewedProjects(String username,
                                                         String projectNameToFilter) {
        if(projectNameToFilter == null || projectNameToFilter.isEmpty()) {
            List<Project> overviewedProjects = projectRepository.getOverviewedProjectsByUser(username);
            return ProjectMapper.toProjectSummaryDTOS(overviewedProjects);
        }
        else{
            List<Project> overviewedProjects = projectRepository.getOverviewedProjectsByUserWithName(username, projectNameToFilter);
            return ProjectMapper.toProjectSummaryDTOS(overviewedProjects);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username,
                                                        String projectNameToFilter) {
        if(projectNameToFilter == null || projectNameToFilter.isEmpty()) {
            List<Project> workingOnProjects = projectRepository.getWorkingOnProjectsByUser(username);
            return ProjectMapper.toProjectSummaryDTOS(workingOnProjects);
        }
        else{
            List<Project> workingOnProjects = projectRepository.getWorkingOnProjectsByUserWithName(username, projectNameToFilter);
            return ProjectMapper.toProjectSummaryDTOS(workingOnProjects);
        }
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject,
                                                      String collaboratorUsername) {
        User newCollaborator = userService.findUserByUsername(collaboratorUsername);

        if(newCollaborator == null)
            throw new  ResourceNotFoundException("Collaborator " + collaboratorUsername + " not found");

        Project referencedProject = projectRepository.getProjectByID(idProject);
        if(referencedProject == null)
            throw new  ResourceNotFoundException("Project not found");

        for(User member: projectRepository.getProjectMembers(idProject))
            if(member.getUsername().equals(collaboratorUsername))
                throw new ResourceAlreadyExistsException("The collaborator you want to add to this project already works for this project.");

        newCollaborator.addToProject(referencedProject);
        projectRepository.save(referencedProject);

        return UserMapper.toUserSummaryDTO(newCollaborator);
    }

    @Override
    public Project getProject(Integer projectId) {
        Project project = projectRepository.getProjectByID(projectId);

        if(project == null)
            throw new ResourceNotFoundException("Project not found");

        return project;
    }
}
