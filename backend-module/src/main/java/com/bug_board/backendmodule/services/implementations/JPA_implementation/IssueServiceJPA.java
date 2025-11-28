package com.bug_board.backendmodule.services.implementations.JPA_implementation;

import com.bug_board.backendmodule.entity.*;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.backendmodule.mappers.IssueMapper;
import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.backendmodule.exception.backend.ResourceNotFoundException;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IssueServiceJPA implements IIssueService {

    private final IIssueRepository issueRepository;

    private final IProjectService projectService;

    private final ILabelService labelService;

    private final IUserService userService;

    public IssueServiceJPA(IIssueRepository issueRepository, IProjectService projectService, ILabelService labelService, IUserService userService) {
        this.labelService = labelService;
        this.issueRepository = issueRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public IssueSummaryDTO publishNewIssueToProject(String usernamePrincipal, Integer idProject, IssueCreationDTO issueToAdd) {
        if(!idProject.equals(issueToAdd.getIdProject()))
            throw new BadRequestException("idProject in the URL and idProject in the issue you want to create don't match.");

        Issue mappedIssue = IssueMapper.toIssue(issueToAdd);

        Project projectOfIssue = projectService.getProject(issueToAdd.getIdProject());
        mappedIssue.setProject(projectOfIssue);

        if(issueToAdd.getIdLabels() != null)
            for (Integer idLabel : issueToAdd.getIdLabels()) {
                Label retrievedLabel = labelService.getLabel(idLabel);

                if(retrievedLabel.getCreatorUsername() != null)
                    if(!usernamePrincipal.equals(retrievedLabel.getCreatorUsername()))
                        throw new AccessDeniedException("The label you want to associate to this issue is not yours.");

                mappedIssue.addLabelAttachedToIssue(retrievedLabel);
            }

        User creator = userService.getUser(usernamePrincipal);
        mappedIssue.setCreator((RegularUser) creator);

        Issue publishedIssue = issueRepository.createANewIssueToProject(mappedIssue);

        return IssueMapper.toIssueSummaryDTO(publishedIssue);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<IssueSummaryDTO> getIssuesOfAUser(String username) {
        List<Issue> issuesRetrieved = issueRepository.retrieveAllUsersIssues(username);
        return IssueMapper.toIssueSummaryDTOS(issuesRetrieved);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public List<IssueSummaryDTO> getIssuesOfAProject(Integer idProject) {
        Project project = projectService.getProject(idProject);

        if(project.getIssues() == null || project.getIssues().isEmpty())
            throw new ResourceNotFoundException("This project doesn't have any issues.");

        return IssueMapper.toIssueSummaryDTOS(project.getIssues());
    }
}
