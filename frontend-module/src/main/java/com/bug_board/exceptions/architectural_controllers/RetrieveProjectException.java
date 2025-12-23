package com.bug_board.exceptions.architectural_controllers;

public class RetrieveProjectException extends WrapperException {
    public RetrieveProjectException(String message, String technicalError) {
        super(message, technicalError);
    }
}
