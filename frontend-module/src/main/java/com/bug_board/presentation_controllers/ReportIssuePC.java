package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.dto.IssueCreationDTO;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.exceptions.views.NoTipologySpecifiedException;
import com.bug_board.exceptions.views.NoTitleDescriptionForIssueException;
import com.bug_board.exceptions.views.NoTitleSpecifiedForIssueException;
import com.bug_board.gui.panes.ReportIssuePane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReportIssuePC {
    private ReportIssuePane reportIssuePane;
    private ReportIssueController reportIssueController;
    private int projectToReport;
    List<Integer> choosenLabels;

    public ReportIssuePC(ReportIssueController reportIssueController, int projectToReport) {
        this.reportIssueController = reportIssueController;
        this.projectToReport = projectToReport;
        this.choosenLabels = new ArrayList<>();
    }

    public void setPane(ReportIssuePane reportIssuePane) {
        this.reportIssuePane = reportIssuePane;
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        return reportIssueController.getUsersLabels();
    }

    public void onConfirmButtonClicked() {
        IssueTipology chosenTiplogy = reportIssuePane.getChoosenIssueTipology();
        String issueTitle = reportIssuePane.getTitle();
        String issueDescription = reportIssuePane.getDescription();
        List<Integer> chosenLabels = reportIssuePane.getChoosenLabels();
        IssuePriority issuePriority = reportIssuePane.getIssuePriority();
        List<byte[]> issueImages =  reportIssuePane.getIssueImages();

        checkTipology(chosenTiplogy);
        checkIssueTitle(issueTitle);
        checkDescription(issueDescription);

        IssueCreationDTO issueToCreate = new IssueCreationDTO(issueTitle, issueDescription,
                chosenTiplogy, issuePriority, issueImages, projectToReport, chosenLabels);


        reportIssueController.createNewIssue(issueToCreate);
    }

    private void checkDescription(String issueDescription) {
        if(issueDescription == null || issueDescription.isBlank())
            throw new NoTitleDescriptionForIssueException("Issue title is empty");
    }

    private void checkIssueTitle(String issueTitle) {
        if(issueTitle == null || issueTitle.isBlank())
            throw new NoTitleSpecifiedForIssueException("Issue title is empty");
    }

    private void checkTipology(IssueTipology choosenTiplogy) {
        if(choosenTiplogy == null)
            throw new NoTipologySpecifiedException("You must select exactly one tipology");
    }
}
