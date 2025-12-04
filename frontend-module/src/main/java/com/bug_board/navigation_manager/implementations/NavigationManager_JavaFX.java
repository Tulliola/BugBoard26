package com.bug_board.navigation_manager.implementations;

import com.bug_board.architectural_controllers.UserProjectController;
import com.bug_board.dao.httphandler.MyHTTPClient;
import com.bug_board.dao.implementations.UserProjectDAO;
import com.bug_board.factories.ProjectFactory;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.presentation_controllers.HomePagePC;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.views.HomePageView;
import javafx.stage.Stage;

public class NavigationManager_JavaFX implements INavigationManager {

    @Override
    public void navigateToHomePageFromLogin(){
        UserProjectController projectController = new UserProjectController(new UserProjectDAO(new MyHTTPClient()));
        HomePagePC homePagePC = new HomePagePC(projectController, new NavigationManager_JavaFX());

        try {
            HomePageView homePageView = new HomePageView(
                    homePagePC,
                    ProjectFactory.getInstance()
                            .setProjectController(projectController)
                            .getProjectsOnBoardByRole(SessionManager.getInstance().getRole())
            );
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
}
