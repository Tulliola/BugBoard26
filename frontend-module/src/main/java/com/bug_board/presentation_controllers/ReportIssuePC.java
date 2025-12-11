package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.dto.LabelSummaryDTO;
import com.bug_board.gui.panes.ReportIssuePane;
import com.bug_board.session_manager.SessionManager;
import com.bug_board.utilities.BugBoardLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReportIssuePC {
    private ReportIssuePane reportIssuePane;
    private ReportIssueController reportIssueController;
    public ReportIssuePC(ReportIssueController reportIssueController) {
        this.reportIssueController = reportIssueController;
        this.reportIssuePane = reportIssuePane;
    }

    public void setPane(ReportIssuePane reportIssuePane) {
        this.reportIssuePane = reportIssuePane;
    }

    public List<LabelSummaryDTO> getUsersLabels() {
        List<LabelSummaryDTO> usersLabels = new ArrayList<>();

        return reportIssueController.getUsersLabels();
    }
}
