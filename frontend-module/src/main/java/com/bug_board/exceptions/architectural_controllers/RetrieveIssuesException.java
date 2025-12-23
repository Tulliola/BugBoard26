package com.bug_board.exceptions.architectural_controllers;

public class RetrieveIssuesException extends WrapperException {
    public RetrieveIssuesException(String message, String technicalError) {
        super(message, technicalError);
    }
}
