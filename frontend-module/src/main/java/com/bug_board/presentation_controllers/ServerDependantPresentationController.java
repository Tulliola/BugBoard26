package com.bug_board.presentation_controllers;

import com.bug_board.gui.panes.ErrorAlert;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import javafx.scene.control.Alert;

public abstract class ServerDependantPresentationController {

    protected final INavigationManager navigationManager;

    public ServerDependantPresentationController(INavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void showErrorAlert(String errorMessage, String technicalError) {
        Alert errorAlert = new ErrorAlert(this.navigationManager, errorMessage, technicalError);
        errorAlert.showAndWait();
    }
}
