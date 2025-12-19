package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.exceptions.architectural_controllers.IssueCreationException;
import com.bug_board.exceptions.views.IssueImageTooLargeException;
import com.bug_board.exceptions.views.NoTipologySpecifiedException;
import com.bug_board.exceptions.views.NoDescriptionForIssueException;
import com.bug_board.exceptions.views.NoTitleSpecifiedForIssueException;
import com.bug_board.gui.panes.ReportIssuePane;
import com.bug_board.gui.panes.TransactionPane;
import javafx.animation.PauseTransition;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.List;

public class ReportIssuePC {
    private ReportIssuePane reportIssuePane;
    private ReportIssueController reportIssueController;
    private int projectToReport;
    private final int ISSUE_IMAGES_MAX_LENGTH = 5 * 1024 * 1024;

    public ReportIssuePC(ReportIssueController reportIssueController, int projectToReport) {
        this.reportIssueController = reportIssueController;
        this.projectToReport = projectToReport;
    }

    public void setPane(ReportIssuePane reportIssuePane) {
        this.reportIssuePane = reportIssuePane;
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        return reportIssueController.getUsersLabels();
    }

    public void onConfirmButtonClicked() throws IssueCreationException {
        IssueTipology chosenTiplogy = this.getSelectedIssueType();
        String issueTitle = this.getIssueTitle();
        String issueDescription = this.getIssueDescription();
        List<Integer> chosenLabels = reportIssuePane.getChoosenLabels();
        IssuePriority issuePriority = this.getIssuePriority();
        List<byte[]> issueImages =  reportIssuePane.getIssueImages();

        checkTipology(chosenTiplogy);
        checkIssueTitle(issueTitle);
        checkDescription(issueDescription);
        checkImagesLength(issueImages);

        IssueCreationDTO issueToCreate = new IssueCreationDTO(issueTitle, issueDescription,
                chosenTiplogy, issuePriority, issueImages, projectToReport, chosenLabels);

        try {
            reportIssueController.createNewIssue(issueToCreate);
        }
        catch (IssueCreationException exc){
            this.addErrorCreatingIssuePane();
            this.showConfirmationPane();

            throw new IssueCreationException(exc.getMessage());
        }

        this.addConfirmationPane();
        this.showConfirmationPane();
    }

    private IssuePriority getIssuePriority() {
        if(reportIssuePane.getPriorityComboBox().getValue() == null)
            return null;
        else if(reportIssuePane.getPriorityComboBox().getValue().equals("Don't specify"))
            return IssuePriority.NO_PRIORITY;
        else if (reportIssuePane.getPriorityComboBox().getValue().equals("Low"))
            return IssuePriority.LOW_PRIORITY;
        else if(reportIssuePane.getPriorityComboBox().getValue().equals("Medium"))
            return IssuePriority.MEDIUM_PRIORITY;
        else
            return IssuePriority.HIGH_PRIORITY;
    }

    private String getIssueTitle() {
        return reportIssuePane.getTitleTextField().getText();
    }

    private String getIssueDescription(){
        return reportIssuePane.getDescriptionTextArea().getText();
    }

    private IssueTipology getSelectedIssueType() {
        if(reportIssuePane.getTypeIssueToggleGroup().getSelectedToggle() == null)
            return null;
        else if(reportIssuePane.getTypeIssueToggleGroup().getSelectedToggle() == reportIssuePane.getBugRadioButton())
            return IssueTipology.BUG;
        else if(reportIssuePane.getTypeIssueToggleGroup().getSelectedToggle() == reportIssuePane.getDocumentationRadioButton())
            return IssueTipology.DOCUMENTATION;
        else if(reportIssuePane.getTypeIssueToggleGroup().getSelectedToggle() == reportIssuePane.getQuestionRadioButton())
            return IssueTipology.QUESTION;
        else if(reportIssuePane.getTypeIssueToggleGroup().getSelectedToggle() == reportIssuePane.getNewFeatureRadioButton())
            return IssueTipology.NEW_FEATURE;
        else
            return null;
    }

    private void checkImagesLength(List<byte[]> issueImages) {
        for(byte[] image: issueImages){
            if(image.length > ISSUE_IMAGES_MAX_LENGTH)
                throw new IssueImageTooLargeException("The issue images' files are too large (max files length: " + ISSUE_IMAGES_MAX_LENGTH / (1024 * 1024) + "MB)");
        }
    }

    private void addErrorCreatingIssuePane() {
        reportIssuePane.getChildren().removeLast();

        TransactionPane errorTransaction = new TransactionPane("/gifs/generic_error.gif", "Issue could not be created");
        errorTransaction.setErrorGradient();

        reportIssuePane.getChildren().add(errorTransaction);

    }

    private void addConfirmationPane() {
        reportIssuePane.getChildren().removeLast();

        reportIssuePane.getChildren().add(new TransactionPane("/gifs/successful_transaction.gif", "Issue reported successfully!"));
    }

    private void showConfirmationPane(){
        PauseTransition delay = new PauseTransition(Duration.seconds(5));

        delay.setOnFinished(event -> {
            reportIssuePane.getParentContainer().getChildren().remove(reportIssuePane);
        });

        delay.play();
    }

    private void checkDescription(String issueDescription) throws NoDescriptionForIssueException{
        if(issueDescription == null || issueDescription.isBlank())
            throw new NoDescriptionForIssueException("Issue's description is empty");
    }

    private void checkIssueTitle(String issueTitle) throws NoTitleSpecifiedForIssueException{
        if(issueTitle == null || issueTitle.isBlank())
            throw new NoTitleSpecifiedForIssueException("Issue's title is empty");
    }

    private void checkTipology(IssueTipology choosenTiplogy) throws NoTipologySpecifiedException{
        if(choosenTiplogy == null)
            throw new NoTipologySpecifiedException("You must select exactly one tipology");
    }

    public void closePane(MouseEvent event) {
        if(event.getTarget() == reportIssuePane)
            reportIssuePane.getParentContainer().getChildren().remove(reportIssuePane);
    }
}