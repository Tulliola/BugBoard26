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
import javafx.util.Duration;

import java.util.ArrayList;
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
        IssueTipology chosenTiplogy = reportIssuePane.getChoosenIssueTipology();
        String issueTitle = reportIssuePane.getTitle();
        String issueDescription = reportIssuePane.getDescription();
        List<Integer> chosenLabels = reportIssuePane.getChoosenLabels();
        IssuePriority issuePriority = reportIssuePane.getIssuePriority();
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
            reportIssuePane.close();
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
}
