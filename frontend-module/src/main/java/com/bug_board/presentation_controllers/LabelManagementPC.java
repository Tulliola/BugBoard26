package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.gui.panes.ConfirmTransactionPane;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.session_manager.SessionManager;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class LabelManagementPC {
    private final UserLabelController  labelController;
    private LabelCreationFormPane labelCreationFormPane;

    public LabelManagementPC(UserLabelController labelController) {
        this.labelController = labelController;
    }

    public void setPane(LabelCreationFormPane labelCreationFormPane) {
        this.labelCreationFormPane = labelCreationFormPane;
    }

    public void onConfirmCreationButtonClicked()
            throws LabelCreationException {
        if(this.labelCreationFormPane == null)
            throw new RuntimeException("Label creation form pane needs to be set");

        String chosenColor = labelCreationFormPane.getChosenColor();
        String labelTitle = labelCreationFormPane.getTitleTextField().getText();
        String labelDescription = labelCreationFormPane.getDescriptionTextArea().getText();
        String username = SessionManager.getInstance().getUsername();

        LabelCreationDTO labelToCreate = new LabelCreationDTO();
        labelToCreate.setCreator(username);
        labelToCreate.setName(labelTitle);
        labelToCreate.setDescription(labelDescription);
        labelToCreate.setColor(chosenColor);

        labelController.createNewLabel(labelToCreate);

        showConfirmationMessage();
    }

    private void showConfirmationMessage() {
        addConfirmationPane();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
           labelCreationFormPane.close();
        });

        delay.play();
    }

    private void addConfirmationPane() {
        labelCreationFormPane.getChildren().removeLast();

        labelCreationFormPane.getChildren().add(new ConfirmTransactionPane("/gifs/successful_transaction.gif", "Label successfully created!"));
    }

}
