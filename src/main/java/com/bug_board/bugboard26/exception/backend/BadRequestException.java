package com.bug_board.bugboard26.exception.backend;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
