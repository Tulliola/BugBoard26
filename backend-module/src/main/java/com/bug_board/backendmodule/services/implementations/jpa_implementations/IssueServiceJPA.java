package com.bug_board.backendmodule.services.implementations.jpa_implementations;

import com.bug_board.backendmodule.entity.*;
import com.bug_board.backendmodule.mappers.IssueImageMapper;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.backendmodule.services.interfaces.IIssueService;
import com.bug_board.backendmodule.services.interfaces.ILabelService;
import com.bug_board.backendmodule.services.interfaces.IProjectService;
import com.bug_board.backendmodule.services.interfaces.IUserService;
import com.bug_board.backendmodule.mappers.IssueMapper;
import com.bug_board.backendmodule.exception.backend.BadRequestException;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
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
    @Transactional
    public List<IssueSummaryDTO> getIssuesOfAUser(String username, IssueFiltersDTO filters) {
        List<Issue> issuesRetrieved = issueRepository.retrieveAllUsersIssues(username, filters);
        return IssueMapper.toIssueSummaryDTOS(issuesRetrieved);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public List<IssueSummaryDTO> getIssuesOfAProject(Integer idProject, IssueFiltersDTO filters) {
        List<Issue> issuesRetrieved = issueRepository.retrieveAllProjectsIssues(idProject, filters);
        return IssueMapper.toIssueSummaryDTOS(issuesRetrieved);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public List<IssueImageDTO> getImagesOfAIssue(Integer projectId, Integer issueId) {
        if(projectId <= 0)
            throw new BadRequestException("idProject in the URL is not valid.");

        Issue issueRetrieved = issueRepository.getIssue(issueId);

        if(issueRetrieved == null)
            throw new BadRequestException("issueId in the URL doesn't correspond to any issue in the database.");

        return IssueImageMapper.toDTOS(issueRetrieved.getImages());
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public List<IssueImageDTO> getImagesOfAIssue(String username, Integer issueId) {
        Issue issueRetrieved = issueRepository.getIssue(issueId);

        if(issueRetrieved == null)
            throw new BadRequestException("issueId in the URL doesn't correspond to any issue in the database.");

        if(!issueRetrieved.getCreator().getUsername().equals(username))
            throw new AccessDeniedException("You are not the creator of this issue.");

        return IssueImageMapper.toDTOS(issueRetrieved.getImages());
    }
}
