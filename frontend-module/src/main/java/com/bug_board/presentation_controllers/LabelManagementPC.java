package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.navigation_manager.interfaces.INavigationManager;

public class LabelManagementPC {
    private final UserLabelController  labelController;
    private final INavigationManager navigationManager;
    private LabelCreationFormPane labelCreationFormPane;

    public LabelManagementPC(UserLabelController labelController, INavigationManager navigationManager) {
        this.labelController = labelController;
        this.navigationManager = navigationManager;
    }

    public void setPane(LabelCreationFormPane labelCreationFormPane) {
        this.labelCreationFormPane = labelCreationFormPane;
    }

    public void onConfirmCreationButtonClicked() {
        if(this.labelCreationFormPane == null)
            throw new RuntimeException("Label creation form pane needs to be set");

        String chosenColor = labelCreationFormPane.getChosenColor();
    }
}
