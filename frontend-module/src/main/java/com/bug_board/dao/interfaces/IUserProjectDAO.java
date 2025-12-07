package com.bug_board.dao.interfaces;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;

import java.util.List;

public interface IUserProjectDAO {
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, ErrorHTTPResponseException;
}
