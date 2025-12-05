package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.dao.BackendErrorException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;

import java.util.List;

public class HomePagePC {
    private final UserProjectController userProjectController;
    private final INavigationManager navigationManager;

    private List<ProjectSummaryDTO> projectsOnBoard;

    public HomePagePC(UserProjectController userProjectController, INavigationManager navigationManager) {
        this.userProjectController = userProjectController;
        this.navigationManager = navigationManager;
    }

    public List<ProjectSummaryDTO> onSearchProjectButtonClick(String projectNameToFilter)
            throws HTTPSendException, BadConversionToDTOException, BackendErrorException {
        if(SessionManager.getInstance().getRole().getRoleName().equals("ROLE_USER"))
            return userProjectController.getWorkingOnProjectsByUser(projectNameToFilter);
        else
            return userProjectController.getOverviewedProjectsByUser(projectNameToFilter);
    }
}
