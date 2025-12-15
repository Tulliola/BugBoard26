package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;

import java.util.List;

public interface IIssueService {
    public IssueSummaryDTO publishNewIssueToProject(String usernamePrincipal, Integer idProject, IssueCreationDTO issueToAdd);
    public List<IssueSummaryDTO> getIssuesOfAUser(String username, IssueFiltersDTO filters);
    public List<IssueSummaryDTO> getIssuesOfAProject(Integer idProject, IssueFiltersDTO filters);
    public List<IssueImageDTO> getImagesOfAIssue(Integer projectId, Integer issueId);
    public List<IssueImageDTO> getImagesOfAIssue(String username, Integer issueId);

}
