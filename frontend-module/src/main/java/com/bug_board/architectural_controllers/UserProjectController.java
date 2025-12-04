package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserProjectDAO;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public class UserProjectController {
    private final IUserProjectDAO userProjectDAO;

    public UserProjectController(IUserProjectDAO userProjectDAO) {
        this.userProjectDAO = userProjectDAO;
    }

    public List<ProjectSummaryDTO> getOverviewedProjectsByUser()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        return userProjectDAO.getOverviewedProjectsByUser();
    }

    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        return userProjectDAO.getWorkingOnProjectsByUser();
    }
}
