package com.bug_board.navigation_manager.interfaces;

import com.bug_board.gui.views.HomePageView;
import com.bug_board.presentation_controllers.HomePagePC;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface INavigationManager {
    void navigateToHomePageFromLogin();

    void closeWindow(Object windowToClose);

    Pane buildLabelCreationComponent(StackPane parentContainer, HomePagePC parentPC);
}
