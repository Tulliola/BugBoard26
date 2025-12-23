package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class UserProjectController {
    private final IUserProjectDAO userProjectDAO;

    public UserProjectController(IUserProjectDAO userProjectDAO) {
        this.userProjectDAO = userProjectDAO;
    }

    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameToFilter)
            throws RetrieveProjectException {
        try {
            return userProjectDAO.getOverviewedProjectsByUser(projectNameToFilter);
        }
        catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException throwables) {
            throw new RetrieveProjectException("There has been an error in retrieving the projects. Please, try later.", throwables.getMessage());
        }
    }

    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameToFilter)
            throws RetrieveProjectException{
        try {
            return userProjectDAO.getWorkingOnProjectsByUser(projectNameToFilter);
        }
        catch (HTTPSendException | BadConversionToDTOException | ErrorHTTPResponseException throwables) {
            throw new RetrieveProjectException("There has been an error in retrieving the issues. Please, try later.", throwables.getMessage());
        }
    }
}
