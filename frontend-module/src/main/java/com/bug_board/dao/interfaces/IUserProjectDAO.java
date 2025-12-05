package com.bug_board.dao.interfaces;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserProjectDAO {
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
}
