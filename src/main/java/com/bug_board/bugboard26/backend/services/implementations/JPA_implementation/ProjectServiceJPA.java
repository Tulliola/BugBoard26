package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IProjectRepository;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
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
        return List.of();
    }

    @Override
    public List<ProjectSummaryDTO> getWorkingOnProjects(String username, String projectNameToFilter) {
        return List.of();
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public UserSummaryDTO assignCollaboratorToProject(Integer idProject, String collaboratorUsername) {
        User newCollaborator = userService.findUserByUsername(collaboratorUsername);
        if(newCollaborator == null)
            throw new  ResourceNotFoundException("Collaborator not found");

        Project referencedProject = projectRepository.getProjectByID(idProject);
        if(referencedProject == null)
            throw new  ResourceNotFoundException("Project not found");

        for(User member: this.getProjectMembers(idProject))
            if(member.getUsername().equals(collaboratorUsername))
                throw new ResourceAlreadyExistsException("The collaborator you want to add to this project already works for this project.");

        newCollaborator.addToProject(referencedProject);
        projectRepository.save(referencedProject);

        return UserMapper.toUserSummaryDTO(newCollaborator);
    }

    @Transactional
    @Override
    public List<User> getProjectMembers(Integer idProject) {
        Project project = projectRepository.getProjectByID(idProject);

        if(project == null)
            throw new  ResourceNotFoundException("Project not found");

        List<User> collaborators = new ArrayList<>();
        collaborators.addAll(project.getAdmins());
        collaborators.addAll(project.getPartecipants());
        collaborators.add(project.getCreator());

        return collaborators;
    }

    @Override
    public Project getProject(Integer projectId) {
        Project project = projectRepository.getProjectByID(projectId);

        if(project == null)
            throw new ResourceNotFoundException("Project not found");

        return project;
    }
}
