package com.bug_board.exceptions.architectural_controllers;

public class RetrieveIssueImagesException extends WrapperException {
    public RetrieveIssueImagesException(String message, String technicalMessage) {
        super(message, technicalMessage);
    }
}
