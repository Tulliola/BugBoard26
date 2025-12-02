package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IIssueDAO {
    public abstract List<IssueSummaryDTO> getAllProjectIssues(Integer idProject) throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
    public abstract IssueSummaryDTO createNewIssue(Integer idProject, IssueCreationDTO issueToCreate) throws BadConversionToJSONException, HTTPSendException, BadConversionToDTOException, BackendErrorException;
}
