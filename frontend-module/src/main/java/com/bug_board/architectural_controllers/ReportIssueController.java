package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IProjectIssueDAO;
import com.bug_board.dao.interfaces.IUserLabelDAO;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.IssueCreationException;
import com.bug_board.exceptions.architectural_controllers.RetrieveLabelsException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.BadConversionToJSONException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class ReportIssueController {
    IProjectIssueDAO userProjectDAO;
    IUserLabelDAO userLabelDAO;

    public ReportIssueController(IProjectIssueDAO userIssueDAO, IUserLabelDAO userLabelDAO) {
        this.userProjectDAO = userIssueDAO;
        this.userLabelDAO = userLabelDAO;
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        try {
            return userLabelDAO.getLabels();
        } catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException e) {
            throw new RetrieveLabelsException(e.getMessage());
        }
    }

    public void createNewIssue(IssueCreationDTO issueToCreate) {
        try {
            userProjectDAO.createNewIssue(issueToCreate.getIdProject(), issueToCreate);
        } catch (ErrorHTTPResponseException | HTTPSendException | BadConversionToDTOException | BadConversionToJSONException e) {
            throw new IssueCreationException(e.getMessage());
        }
    }
}
