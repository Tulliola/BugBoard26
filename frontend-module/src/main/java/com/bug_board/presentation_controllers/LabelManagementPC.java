package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.exceptions.architectural_controllers.LabelDeleteException;
import com.bug_board.gui.panes.AllLabelsPane;
import com.bug_board.gui.panes.ConfirmDeleteLabelDialog;
import com.bug_board.gui.panes.TransactionPane;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.BugBoardLabel;
import javafx.animation.PauseTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class LabelManagementPC {
    private final UserLabelController  labelController;
    private LabelCreationFormPane labelCreationFormPane;
    private AllLabelsPane allLabelsPane;
    private final List<LabelSummaryDTO> labels;

    public LabelManagementPC(UserLabelController labelController) {
        this.labelController = labelController;
        this.labels = labelController.getUsersLabels();
    }

    public void setCreationPane(LabelCreationFormPane labelCreationFormPane) {
        this.labelCreationFormPane = labelCreationFormPane;
    }

    public void setVisualizationPane(AllLabelsPane allLabelsPane) {
        this.allLabelsPane = allLabelsPane;
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

        showConfirmationCreationMessage("Label successfully created!");
    }

    public void onDeleteButtonClicked(BugBoardLabel labelToDelete, StackPane parentContainer) {
        ConfirmDeleteLabelDialog confirmDialog = new ConfirmDeleteLabelDialog(labelToDelete, this, parentContainer);
        allLabelsPane.showConfirmationDialog(confirmDialog);
    }

    public void onConfirmDeleteButtonClicked(Integer idLabel) {
        try {
            labelController.deleteLabel(idLabel);
            showConfirmationDeleteMessage("Label successfully deleted!");
        } catch (LabelDeleteException e) {
            showDeleteError();
        }
    }

    public List<LabelSummaryDTO> getUserLabels() {
        return labels;
    }

    public List<LabelSummaryDTO> getFilteredLabels(String text) {
        return labels
                .stream()
                .filter(label -> label.getName().toLowerCase().contains(text.toLowerCase()))
                .toList();
    }

    private void showConfirmationDeleteMessage(String text) {
        allLabelsPane.getChildren().removeLast();
        allLabelsPane.getChildren().removeLast();

        allLabelsPane.getChildren().add(new TransactionPane("/gifs/successful_transaction.gif", text));

        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            allLabelsPane.close();
        });

        delay.play();
    }

    private void showConfirmationCreationMessage(String text) {
        labelCreationFormPane.getChildren().removeLast();
        labelCreationFormPane.getChildren().add(new TransactionPane("/gifs/successful_transaction.gif", text));

        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
           labelCreationFormPane.close();
        });

        delay.play();
    }
    
    private void showDeleteError() {
        allLabelsPane.getChildren().removeLast();
        allLabelsPane.getChildren().removeLast();

        TransactionPane transactionPane = new TransactionPane("/gifs/generic_error.gif", "Couldn't delete the label. Please, try later.");
        transactionPane.setErrorGradient();

        allLabelsPane.getChildren().add(transactionPane);
    }
}
