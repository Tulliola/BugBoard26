package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dto.ProjectSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.RetrieveProjectException;
import com.bug_board.exceptions.dao.ErrorHTTPResponseException;
import com.bug_board.exceptions.dao.BadConversionToDTOException;
import com.bug_board.exceptions.dao.HTTPSendException;
import com.bug_board.gui.views.HomePageView;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HomePagePC {
    private final UserProjectController userProjectController;
    private final INavigationManager navigationManager;
    private HomePageView homePageView;

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

    public void closeOverlay(Region layerToRemove) {
        homePageView.getContainerUnderTitleBar().getChildren().remove(layerToRemove);
    }

    public void setView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }
}
