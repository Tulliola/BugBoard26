package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IProjectIssueDAO;
import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class ProjectIssueController {
    private final IProjectIssueDAO projectIssueDAO;

    public ProjectIssueController(IProjectIssueDAO projectIssueDAO) {this.projectIssueDAO = projectIssueDAO;}

    public List<IssueSummaryDTO> getProjectIssues(Integer idProject)
            throws RetrieveIssuesException {
        try {
            return projectIssueDAO.getAllProjectIssues(idProject);
        }
        catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException throwables) {
            throw new RetrieveIssuesException("There has been an error in retrieving the issues. Try later");
        }
    }
}
