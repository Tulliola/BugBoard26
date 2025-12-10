package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ProjectIssueController;
import com.bug_board.architectural_controllers.UserIssueController;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.gui.views.IssueVisualizationView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import javafx.scene.control.Alert;

public class IssueVisualizationPC {
    private UserIssueController userIssueController;
    private ProjectIssueController projectIssueController;
    private final INavigationManager navigationManager;
    private IssueVisualizationView issueView;

    public IssueVisualizationPC(UserIssueController userIssueController,
                                INavigationManager navigationManager) {
        this.userIssueController = userIssueController;
        this.navigationManager = navigationManager;
    }

    public IssueVisualizationPC(ProjectIssueController projectIssueController,
                                INavigationManager navigationManager) {
        this.projectIssueController = projectIssueController;
        this.navigationManager = navigationManager;
    }

    public void setView(IssueVisualizationView issueView) {
        this.issueView = issueView;
    }

    public void onGoBackButtonClicked() {
        navigationManager.closeWindow(this.issueView);
        try {
            navigationManager.navigateToHomePage();
        }
        catch (RetrieveProjectException e) {

        }
    }

    public void showIssuesRetrievalError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Server error");
        alert.setHeaderText("Couldn't retrieve the issues.");
        alert.setContentText("Server's not responding. You can visualize the issue page, but it will be empty.");
        alert.showAndWait();
    }
}
