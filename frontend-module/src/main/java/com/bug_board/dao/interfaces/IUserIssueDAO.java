package com.bug_board.dao.interfaces;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserIssueDAO {
    public abstract List<IssueSummaryDTO> getPersonalIssues()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
}
