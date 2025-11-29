package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueSummaryDTO;

import java.util.List;

public interface IUserIssueDAO {
    public abstract List<IssueSummaryDTO> getPersonalIssues();
}
