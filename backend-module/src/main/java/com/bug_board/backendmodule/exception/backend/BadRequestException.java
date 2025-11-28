package com.bug_board.backendmodule.exception.backend;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
