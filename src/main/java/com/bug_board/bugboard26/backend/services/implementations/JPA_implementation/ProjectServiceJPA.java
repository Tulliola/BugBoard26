package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.backend.services.mappers.ProjectMapper;
import com.bug_board.bugboard26.backend.services.mappers.UserMapper;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import com.bug_board.bugboard26.exception.backend.ResourceAlreadyExistsException;
import com.bug_board.bugboard26.exception.backend.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceJPA implements IProjectService {

    private final IProjectRepository projectRepository;

    private final IUserService userService;

    public ProjectServiceJPA(IProjectRepository projectRepository, IUserService userRepository) {
        this.userService = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectSummaryDTO> getOverviewedProjects(String username, String projectNameToFilter) {
        if(projectNameToFilter == null || projectNameToFilter.equals("")) {
            List<Project> overviewedProjects = projectRepository.getWorkingOnProjectsByUser(username);
            return overviewedProjects.stream().map(ProjectMapper::toProjectSummaryDTO).toList();
        }
        else{
            List<Project> overviewedProjects = projectRepository.getWorkingOnProjectsByUserWithName(username, projectNameToFilter);
            return overviewedProjects.stream().map(ProjectMapper::toProjectSummaryDTO).toList();
        }
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter) {
        if(projectNameToFilter == null || projectNameToFilter.equals("")) {
            List<Project> workingOnProjects = projectRepository.getOverviewedProjectsByUser(username);
            return workingOnProjects.stream().map(ProjectMapper::toProjectSummaryDTO).toList();
        }
        else{
            List<Project> workingOnProjects = projectRepository.getOverviewedProjectsByUserWithName(username, projectNameToFilter);
            return workingOnProjects.stream().map(ProjectMapper::toProjectSummaryDTO).toList();
        }
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject, String collaboratorUsername) {
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
