package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.navigation_manager.interfaces.INavigationManager;

import java.util.List;

public class HomePagePC {
    private final UserProjectController userProjectController;
    private final INavigationManager navigationManager;

    private List<ProjectSummaryDTO> projectsOnBoard;

    public HomePagePC(UserProjectController userProjectController, INavigationManager navigationManager) {
        this.userProjectController = userProjectController;
        this.navigationManager = navigationManager;
    }
}
