package com.bug_board.dao.interfaces;

import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.dto.UserAuthenticationDTO;

import java.util.List;

public interface IUserProjectDAO {
    public List<ProjectSummaryDTO> getOverviewedProjectsByUser(String projectNameFilter);
    public List<ProjectSummaryDTO> getWorkingOnProjectsByUser(String projectNameFilter);
}
