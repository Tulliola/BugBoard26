package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.*;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import com.bug_board.bugboard26.backend.services.interfaces.ILabelService;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.backend.services.mappers.IssueMapper;
import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import com.bug_board.bugboard26.exception.backend.BadRequestException;
import com.bug_board.bugboard26.exception.backend.ResourceNotFoundException;
import jakarta.transaction.Transactional;
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

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public IssueSummaryDTO publishNewIssueToProject(Integer idProject, IssueCreationDTO issueToAdd) {
        if(!idProject.equals(issueToAdd.getIdProject()))
            throw new BadRequestException("idProject in the URL and idProject in the issue you want to create don't match.");

        Issue mappedIssue = IssueMapper.toIssue(issueToAdd);

        Project projectOfIssue = projectService.getProject(issueToAdd.getIdProject());
        mappedIssue.setProject(projectOfIssue);

        for (Integer idLabel : issueToAdd.getIdLabels()){
            Label retrievedLabel = labelService.getLabel(idLabel);
            mappedIssue.addLabelAttachedToIssue(retrievedLabel);
        }

        User creator = userService.getUser(issueToAdd.getUsername());
        mappedIssue.setCreator((RegularUser) creator);

        Issue publishedIssue = issueRepository.createANewIssueToProject(mappedIssue);

        return IssueMapper.toIssueSummaryDTO(publishedIssue);
    }

    @Override
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
