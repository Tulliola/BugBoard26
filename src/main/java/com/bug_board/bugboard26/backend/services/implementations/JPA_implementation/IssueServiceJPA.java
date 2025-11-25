package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.Issue;
import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import com.bug_board.bugboard26.backend.services.interfaces.IProjectService;
import com.bug_board.bugboard26.backend.services.mappers.IssueMapper;
import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import com.bug_board.bugboard26.dto.ProjectSummaryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueServiceJPA implements IIssueService {

    private final IIssueRepository issueRepository;

    private final IProjectService projectService;

    public IssueServiceJPA(IIssueRepository issueRepository, IProjectService projectService) {
        this.issueRepository = issueRepository;
        this.projectService = projectService;
    }

    @Transactional
    @Override
    public IssueSummaryDTO publishNewIssueToProject(IssueCreationDTO issueToAdd) {
        //TODO completare questo metodo

        Issue mappedIssue = IssueMapper.toIssue(issueToAdd);

        Project issuesProject = projectService.getProjectById(issueToAdd.getIdProject());
        mappedIssue.setProject(issuesProject);

        Issue publishedIssue = issueRepository.createANewIssueToProject(issueToAdd.getIdProject(), mappedIssue);
        return IssueMapper.toIssueSummaryDTO(publishedIssue);
    }

    @Override
    public List<IssueSummaryDTO> getIssueOfAUser(String username) {
        return List.of();
    }

    @Override
    public List<IssueSummaryDTO> getIssueOfAProject(Integer idProject) {
        return List.of();
    }
}
