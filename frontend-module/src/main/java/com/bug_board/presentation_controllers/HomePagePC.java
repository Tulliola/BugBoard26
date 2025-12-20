package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.gui.panes.TransactionPane;
import com.bug_board.gui.views.HomePageView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class HomePagePC {
    private final UserProjectController userProjectController;
    private final INavigationManager navigationManager;
    private HomePageView homePageView;
    private List<ProjectSummaryDTO> projectsRetrieved;

    private final int MAX_PROJECTS_PER_PAGE = 3;

    public HomePagePC(
                      UserProjectController userProjectController,
                      INavigationManager navigationManager) {
        this.userProjectController = userProjectController;
        this.navigationManager = navigationManager;
    }

    public List<ProjectSummaryDTO> onSearchProjectButtonClick(String projectNameToFilter)
            throws RetrieveProjectException {
        if(SessionManager.getInstance().getRole().getRoleName().equals("ROLE_USER"))
            return userProjectController.getWorkingOnProjectsByUser(projectNameToFilter);
        else
            return userProjectController.getOverviewedProjectsByUser(projectNameToFilter);
    }

    public void showLabelCreationOverlay(StackPane container) {
        Pane labelCreationOverlay = navigationManager.buildLabelCreationComponent(container, this);

        homePageView.displayOverlayedContent(labelCreationOverlay);
    }

    public void showRegisterUserOverlay(StackPane containerUnderTitleBar) {
        Pane registerUserOverlay = navigationManager.buildRegisterUserComponent(containerUnderTitleBar, this);

        homePageView.displayOverlayedContent(registerUserOverlay);
    }

    public void onViewPersonalIssuesButtonClicked() {
        navigationManager.closeWindow(this.homePageView);
        try {
            navigationManager.navigateToViewPersonalIssues();
        }
        catch (RetrieveIssuesException e) {
            TransactionPane errorPane = new TransactionPane("/gifs/generic_error.gif", e.getMessage());
            errorPane.setErrorGradient();

            homePageView.displayOverlayedContent(errorPane);
        }
    }

    public void showProjectsRetrievalError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Server error");
        alert.setHeaderText("Couldn't retrieve the projects.");
        alert.setContentText("Server's not responding. You can visualize the home page, but it will be empty.");
        alert.showAndWait();
    }


    public void setView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public void showReportIssueOverlay(StackPane containerUnderTitleBar, int projectReported) {
        Pane reportIssueOverlay = navigationManager.buildReportIssueComponent(containerUnderTitleBar, projectReported, this);

        homePageView.displayOverlayedContent(reportIssueOverlay);
    }

    public List<ProjectSummaryDTO> getProjectsOfAPage(int pageIndex){
        List<ProjectSummaryDTO> projectsOfPage;

        int start = pageIndex * MAX_PROJECTS_PER_PAGE;
        int end = Math.min(start + MAX_PROJECTS_PER_PAGE, projectsRetrieved.size());
        projectsOfPage = projectsRetrieved.subList(start, end);

        return projectsOfPage;
    }

    public void setProjectsRetrieved(List<ProjectSummaryDTO> projectsRetrieved) {
        this.projectsRetrieved = projectsRetrieved;
    }

    public StackPane getContainer() {
        return homePageView.getContainerUnderTitleBar();
    }

    public void openVisualizationIssueView(Integer idProject, String projectName) {
        this.navigationManager.closeWindow(this.homePageView);
        this.navigationManager.navigateToViewProjectIssues(idProject, projectName);
    }
}
