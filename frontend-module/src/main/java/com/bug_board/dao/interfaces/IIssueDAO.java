package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueSummaryDTO;

import java.util.List;

public interface IIssueDAO {
    public abstract List<IssueSummaryDTO> getAllProjectIssues(Integer idProject);
    public abstract IssueSummaryDTO createNewIssue(Integer idProject, IssueSummaryDTO issueToCreate);
}
