package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueImageDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserIssueDAO {
    public abstract List<IssueSummaryDTO> getPersonalIssues(IssueFiltersDTO filters)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException, BadConversionToJSONException;

    List<IssueImageDTO> getAllIssueImages(Integer idIssue)
            throws ErrorHTTPResponseException, HTTPSendException, BadConversionToDTOException;
}
