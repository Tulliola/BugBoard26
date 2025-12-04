package com.bug_board.dao.interfaces;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserProjectDAO {
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser()
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException;
}
