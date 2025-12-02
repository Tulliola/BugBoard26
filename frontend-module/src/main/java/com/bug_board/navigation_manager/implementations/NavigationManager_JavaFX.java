package com.bug_board.navigation_manager.implementations;

import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;
import javafx.stage.Stage;

public class NavigationManager_JavaFX implements INavigationManager {

    @Override
    public void navigateToHomePageFromLogin(){
        System.out.println("Passaggio alla view HomePage.");
        System.out.println(SessionManager.getInstance().getJwtToken());
    }

    @Override
    public void closeWindow(Object windowToClose){
        if(windowToClose instanceof Stage)
            ((Stage) windowToClose).close();
    }
}
