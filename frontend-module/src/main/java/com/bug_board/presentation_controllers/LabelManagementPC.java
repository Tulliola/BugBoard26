package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.UserLabelController;
import com.bug_board.dto.LabelCreationDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.exceptions.architectural_controllers.LabelCreationException;
import com.bug_board.gui.panes.AllLabelsPane;
import com.bug_board.gui.panes.TransactionPane;
import com.bug_board.gui.panes.LabelCreationFormPane;
import com.bug_board.session_manager.SessionManager;
import javafx.animation.PauseTransition;
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

        showConfirmationMessage();
    }

    public void onDeleteButtonClicked() {

    }

    public List<LabelSummaryDTO> getUserLabels() {
        return labels;
    }

    public List<LabelSummaryDTO> getFilteredLabels(String text) {
        return labels
                .stream()
                .filter(label -> label.getDescription().toLowerCase().contains(text.toLowerCase()))
                .toList();
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

        labelCreationFormPane.getChildren().add(new TransactionPane("/gifs/successful_transaction.gif", "Label successfully created!"));
    }
}
