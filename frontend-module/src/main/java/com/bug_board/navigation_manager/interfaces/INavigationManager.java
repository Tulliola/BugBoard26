package com.bug_board.navigation_manager.interfaces;

import com.bug_board.exceptions.architectural_controllers.RetrieveIssuesException;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.presentation_controllers.HomePagePC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface INavigationManager {
    void navigateToHomePage() throws RetrieveProjectException;

    void navigateToViewPersonalIssues() throws RetrieveIssuesException;

    void navigateToViewProjectIssues(Integer idProject, String projectName) throws RetrieveIssuesException;

    void closeWindow(Object windowToClose);

    Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC);

    Pane buildRegisterUserComponent(StackPane containerUnderTitleBar, HomePagePC homePagePC);

    Pane buildReportIssueComponent(StackPane containerUnderTitleBar, HomePagePC homePagePC);
}
