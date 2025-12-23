package com.bug_board.exceptions.architectural_controllers;

public class WrapperException extends Exception{
    private final String technicalMessage;

    public WrapperException(String message, String technicalMessage) {
        super(message);

        this.technicalMessage = technicalMessage;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }
}
