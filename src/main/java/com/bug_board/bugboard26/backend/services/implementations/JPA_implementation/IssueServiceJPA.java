package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.services.interfaces.IIssueService;
import com.bug_board.bugboard26.dto.IssueCreationDTO;
import com.bug_board.bugboard26.dto.IssueSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceJPA implements IIssueService {

    @Override
    public void publishNewIssueToProject(Integer idProject, IssueCreationDTO issueToAdd) {

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
