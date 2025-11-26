package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IIssueService {
    public IssueSummaryDTO publishNewIssueToProject(Integer idProject, IssueCreationDTO issueToAdd);
    public List<IssueSummaryDTO> getIssuesOfAUser(String username);
    public List<IssueSummaryDTO> getIssuesOfAProject(Integer idProject);
}
