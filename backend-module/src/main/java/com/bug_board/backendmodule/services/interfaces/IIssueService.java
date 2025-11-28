package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;

import java.util.List;

public interface IIssueService {
    public IssueSummaryDTO publishNewIssueToProject(String usernamePrincipal, Integer idProject, IssueCreationDTO issueToAdd);
    public List<IssueSummaryDTO> getIssuesOfAUser(String username);
    public List<IssueSummaryDTO> getIssuesOfAProject(Integer idProject);
}
