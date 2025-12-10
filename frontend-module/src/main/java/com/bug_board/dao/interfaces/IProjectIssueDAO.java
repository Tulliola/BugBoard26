package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IProjectIssueDAO {
    public abstract List<IssueSummaryDTO> getAllProjectIssues(Integer idProject) throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
    public abstract IssueSummaryDTO createNewIssue(Integer idProject, IssueCreationDTO issueToCreate) throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
}
