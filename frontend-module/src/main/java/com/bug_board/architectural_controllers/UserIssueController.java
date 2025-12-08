package com.bug_board.architectural_controllers;

import com.bug_board.dao.interfaces.IUserIssueDAO;

public class UserIssueController {
    private final IUserIssueDAO userIssueDAO;

    public UserIssueController(IUserIssueDAO userIssueDAO){
        this.userIssueDAO = userIssueDAO;
    }
}
