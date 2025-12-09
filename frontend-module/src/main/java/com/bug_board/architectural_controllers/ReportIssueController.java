package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserIssueDAO;

public class ReportIssueController {
    IUserIssueDAO userIssueDAO;

    public ReportIssueController(IUserIssueDAO userIssueDAO) {
        this.userIssueDAO = userIssueDAO;
    }
}
