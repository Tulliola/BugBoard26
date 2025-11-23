package com.bug_board.bugboard26.backend.services_interfaces;

import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IIssueService {
    public void publishNewIssueToProject(Integer idProject, IssueCreationDTO issueToAdd);
    public List<IssueSummaryDTO> getIssueOfAUser(String username);
    public List<IssueSummaryDTO> getIssueOfAProject(Integer idProject);
}
