package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelModifyingDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.exceptions.architectural_controllers.LabelDeleteException;
import com.bug_board.exceptions.architectural_controllers.LabelModifyingException;
import com.bug_board.exceptions.architectural_controllers.RetrieveLabelsException;
import com.bug_board.gui.panes.*;
import com.bug_board.navigation_manager.interfaces.INavigationManager;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.BugBoardLabel;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;

public class LabelManagementPC extends ServerDependantPresentationController{
    private final UserLabelController  labelController;

    private LabelCreationFormPane labelCreationFormPane;
    private LabelModifyingFormPane labelModifyingFormPane;
    private AllLabelsPane allLabelsPane;

    private List<LabelSummaryDTO> labels;

    public LabelManagementPC(INavigationManager navigationManager,
                             UserLabelController labelController) {
        super(navigationManager);
        this.labelController = labelController;
    }

    public void setLabels() throws RetrieveLabelsException {
        this.labels = labelController.getUsersLabels();
    }

    public void setCreationPane(LabelCreationFormPane labelCreationFormPane) {
        this.labelCreationFormPane = labelCreationFormPane;
    }

    public void setVisualizationPane(AllLabelsPane allLabelsPane) {
        this.allLabelsPane = allLabelsPane;
    }

    public void setModifyingPane(LabelModifyingFormPane labelModifyingFormPane) {
        this.labelModifyingFormPane = labelModifyingFormPane;
    }

    public void onConfirmCreationButtonClicked() {
        if(this.labelCreationFormPane == null)
            throw new RuntimeException("Label creation form pane needs to be set");

        LabelCreationDTO labelToCreate = getLabelCreationDTO();

        try {
            labelController.createNewLabel(labelToCreate);
            showConfirmationCreationMessage("Label successfully created!");
        }
        catch(LabelCreationException exc){
            showCreationError();
        }

    }

    private LabelCreationDTO getLabelCreationDTO() {
        String chosenColor = labelCreationFormPane.getChosenColor();
        String labelTitle = labelCreationFormPane.getTitleTextField().getText();
        String labelDescription = labelCreationFormPane.getDescriptionTextArea().getText();
        String username = SessionManager.getInstance().getUsername();

        LabelCreationDTO labelToCreate = new LabelCreationDTO();
        labelToCreate.setCreator(username);
        labelToCreate.setName(labelTitle);
        labelToCreate.setDescription(labelDescription);
        labelToCreate.setColor(chosenColor);

        return labelToCreate;
    }

    public void onDeleteButtonClicked(BugBoardLabel labelToDelete, StackPane parentContainer) {
        ConfirmDeleteLabelDialog confirmDialog = new ConfirmDeleteLabelDialog(labelToDelete, this, parentContainer);
        allLabelsPane.showConfirmationDialog(confirmDialog);
    }

    public void onModifyButtonClicked(BugBoardLabel labelToModify, StackPane parentContainer) {
        LabelFormPane modifyFormPane = new LabelModifyingFormPane(labelToModify, this, parentContainer);
        allLabelsPane.showOverlayedContent(modifyFormPane);
    }

    public void onConfirmDeleteButtonClicked(Integer idLabel) {
        try {
            labelController.deleteLabel(idLabel);
            showConfirmationDeleteOrModifyMessage("Label successfully deleted!");
        }
        catch (LabelDeleteException e) {
            showModifyingOrDeleteError("Couldn't delete the label. Please, try later.");
        }
    }

    public void onConfirmModifyingButtonClicked() {
        LabelModifyingDTO modifyingDTO = getLabelModifyingDTO();

        try {
            labelController.modifyLabel(modifyingDTO);
            showConfirmationDeleteOrModifyMessage("Label successfully modified!");
        }
        catch (LabelModifyingException e) {
            showModifyingOrDeleteError("Couldn't modify the label. Please, try later");
        }
    }

    public LabelModifyingDTO getLabelModifyingDTO() {
        Integer idLabel = this.labelModifyingFormPane.getIdLabel();
        String labelTitle = labelModifyingFormPane.getTitleTextField().getText();
        String labelDescription = labelModifyingFormPane.getDescriptionTextArea().getText();
        String chosenColor = labelModifyingFormPane.getChosenColor();

        LabelModifyingDTO labelModifyingDTO = new LabelModifyingDTO();
        labelModifyingDTO.setIdLabel(idLabel);
        labelModifyingDTO.setName(labelTitle);
        labelModifyingDTO.setDescription(labelDescription);
        labelModifyingDTO.setColor(chosenColor);

        return labelModifyingDTO;
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

    private void showConfirmationDeleteOrModifyMessage(String text) {
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

    private void showCreationError() {
        labelCreationFormPane.getChildren().removeLast();

        TransactionPane transactionPane = new TransactionPane("/gifs/generic_error.gif", "Couldn't create the label. Please, try later.");
        transactionPane.setErrorGradient();

        labelCreationFormPane.getChildren().add(transactionPane);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            labelCreationFormPane.close();
        });

        delay.play();
    }

    private void showModifyingOrDeleteError(String text) {
        allLabelsPane.getChildren().removeLast();
        allLabelsPane.getChildren().removeLast();

        TransactionPane transactionPane = new TransactionPane("/gifs/generic_error.gif", text);
        transactionPane.setErrorGradient();

        allLabelsPane.getChildren().add(transactionPane);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            allLabelsPane.close();
        });

        delay.play();
    }
}
