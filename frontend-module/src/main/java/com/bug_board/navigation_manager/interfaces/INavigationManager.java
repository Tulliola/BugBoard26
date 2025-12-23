package com.bug_board.navigation_manager.interfaces;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.presentation_controllers.IssueVisualizationPC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface INavigationManager {
    void navigateToHomePage();

    void navigateToViewPersonalIssues();

    void navigateToViewProjectIssues(Integer idProject, String projectName);

    void closeWindow(Object windowToClose);

    Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC);

    Pane buildRegisterUserComponent(StackPane containerUnderTitleBar, HomePagePC homePagePC);

    Pane buildIssueSummaryComponent(StackPane containerUnderTitleBar, IssueSummaryDTO issueToShow, IssueVisualizationPC issuePC);

    Pane buildReportIssueComponent(StackPane containerUnderTitleBar, int projectToReport);

    Pane buildImageViewComponent(StackPane containerUnderTitleBar, byte[] imageToView);

    Pane buildAllLabelsComponent(StackPane containerUnderTitleBar);

    void navigateToLoginPage();
}
