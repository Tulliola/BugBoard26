package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class UserProjectController {
    private final IUserProjectDAO userProjectDAO;

    public UserProjectController(IUserProjectDAO userProjectDAO) {
        this.userProjectDAO = userProjectDAO;
    }

    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        return userProjectDAO.getOverviewedProjectsByUser(projectNameToFilter);
    }

    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        return userProjectDAO.getWorkingOnProjectsByUser(projectNameToFilter);
    }
}
