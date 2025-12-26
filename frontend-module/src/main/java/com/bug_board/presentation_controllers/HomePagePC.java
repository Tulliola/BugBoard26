package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.gui.views.HomePageView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class HomePagePC extends ServerDependantPresentationController {
    private final UserProjectController userProjectController;
    private HomePageView homePageView;
    private List<ProjectSummaryDTO> projectsRetrieved;

    private final int MAX_PROJECTS_PER_PAGE = 3;

    public HomePagePC(
                      UserProjectController userProjectController,
                      INavigationManager navigationManager) {
        super(navigationManager);
        this.userProjectController = userProjectController;
    }

    public List<ProjectSummaryDTO> onSearchProjectButtonClick(String projectNameToFilter) {
        try {
            if (SessionManager.getInstance().getRole().getRoleName().equals("ROLE_USER"))
                return userProjectController.getWorkingOnProjectsByUser(projectNameToFilter);
            else
                return userProjectController.getOverviewedProjectsByUser(projectNameToFilter);
        }
        catch(RetrieveProjectException exc) {
            navigationManager.closeWindow(homePageView);
            showErrorAlert("There has been an error filtering your projects. Please, try later.", exc.getTechnicalMessage());
        }

        return new ArrayList<>();
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

        navigationManager.navigateToViewPersonalIssues();
    }


    public void setView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public void showReportIssueOverlay(StackPane containerUnderTitleBar, int projectReported) {
        Pane reportIssueOverlay = navigationManager.buildReportIssueComponent(containerUnderTitleBar, projectReported);

        homePageView.displayOverlayedContent(reportIssueOverlay);
    }


    public void showAllLabelsOverlay(StackPane containerUnderTitleBar) {
        Pane modifyLabelOverlay = navigationManager.buildAllLabelsComponent(containerUnderTitleBar);

        homePageView.displayOverlayedContent(modifyLabelOverlay);
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
