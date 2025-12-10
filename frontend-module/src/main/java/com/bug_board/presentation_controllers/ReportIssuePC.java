package com.bug_board.presentation_controllers;

import com.bug_board.architectural_controllers.ReportIssueController;
import com.bug_board.gui.panes.ReportIssuePane;

public class ReportIssuePC {
    private ReportIssuePane reportIssuePane;

    public ReportIssuePC(ReportIssueController reportIssueController) {
    }

    public void setPane(ReportIssuePane reportIssuePane) {
        this.reportIssuePane = reportIssuePane;
    }
}
