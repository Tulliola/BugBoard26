package com.bug_board.navigation_manager.implementations;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.UserLabelDAO_REST;
import com.bug_board.dao.implementations.UserProjectDAO;
import com.bug_board.factories.ProjectFactory;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.presentation_controllers.LabelManagementPC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.gui.views.HomePageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NavigationManager_JavaFX implements INavigationManager {

    @Override
    public void navigateToHomePageFromLogin(){
        UserProjectController projectController = new UserProjectController(new UserProjectDAO(new MyHTTPClient()));
        HomePagePC homePagePC = new HomePagePC(projectController, this);

        try {
            HomePageView homePageView = new HomePageView(
                    homePagePC,
                    ProjectFactory.getInstance()
                            .setProjectController(projectController)
                            .getProjectsOnBoardByRole(SessionManager.getInstance().getRole())
            );
            homePagePC.setView(homePageView);

            homePageView.show();
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void closeWindow(Object windowToClose){
        if(windowToClose instanceof Stage)
            ((Stage) windowToClose).close();
    }

    @Override
    public Pane buildLabelCreationComponent(StackPane parentContainer) {
        UserLabelController labelController = new UserLabelController(new UserLabelDAO_REST(new MyHTTPClient()));

        LabelManagementPC labelPC = new LabelManagementPC(labelController, this);

        LabelCreationFormPane labelCreationFormPane = new LabelCreationFormPane(labelPC, parentContainer);

        labelPC.setPane(labelCreationFormPane);

        return labelCreationFormPane;
    }
}
