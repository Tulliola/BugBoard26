package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserIssueDAO;
import com.bug_board.dao.interfaces.IUserLabelDAO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class ReportIssueController {
    IUserIssueDAO userIssueDAO;
    IUserLabelDAO userLabelDAO;

    public ReportIssueController(IUserIssueDAO userIssueDAO, IUserLabelDAO userLabelDAO) {
        this.userIssueDAO = userIssueDAO;
        this.userLabelDAO = userLabelDAO;
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        try {
            return userLabelDAO.getLabels();
        } catch (HTTPSendException e) {
            throw new RuntimeException(e);
        } catch (BadConversionToDTOException e) {
            throw new RuntimeException(e);
        } catch (ErrorHTTPResponseException e) {
            throw new RuntimeException(e);
        }
    }
}
