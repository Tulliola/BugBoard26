package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserIssueDAO;
import com.bug_board.dto.IssueFiltersDTO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class UserIssueController {
    private final IUserIssueDAO userIssueDAO;

    public UserIssueController(IUserIssueDAO userIssueDAO){
        this.userIssueDAO = userIssueDAO;
    }

    public List<IssueSummaryDTO> getPersonalIssues(IssueFiltersDTO filters)
            throws RetrieveIssuesException {
        try {
            return userIssueDAO.getPersonalIssues(filters);
        }
        catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException |
               BadConversionToJSONException throwables) {
            throw new RetrieveIssuesException("There has been an error in retrieving your personal issues. Try later");
        }
    }
}
