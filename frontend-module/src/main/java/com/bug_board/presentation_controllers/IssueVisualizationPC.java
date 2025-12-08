package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;

public class IssueVisualizationPC {
    private final UserIssueController userIssueController;
    private final INavigationManager navigationManager;
    private IssueVisualizationView issueView;

    public IssueVisualizationPC(UserIssueController userIssueController,
                                INavigationManager navigationManager) {
        this.userIssueController = userIssueController;
        this.navigationManager = navigationManager;
    }

    public void setView(IssueVisualizationView issueView) {
        this.issueView = issueView;
    }


}
