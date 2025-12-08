package com.bug_board.navigation_manager.interfaces;

import com.bug_board.exceptions.architectural_controllers.RetrievePersonalIssuesException;
import com.bug_board.presentation_controllers.HomePagePC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface INavigationManager {
    void navigateToHomePage();

    void navigateToViewIssues() throws RetrievePersonalIssuesException;

    void closeWindow(Object windowToClose);

    Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC);

    Pane buildRegisterUserComponent(StackPane containerUnderTitleBar, HomePagePC homePagePC);
}
