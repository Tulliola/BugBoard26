package com.bug_board.exceptions.architectural_controllers;

public class RetrieveLabelsException extends WrapperException {
    public RetrieveLabelsException(String message, String technicalMessage) {
        super(message, technicalMessage);
    }
}
