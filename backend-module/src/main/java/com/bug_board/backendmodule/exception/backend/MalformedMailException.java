package com.bug_board.backendmodule.exception.backend;

public class MalformedMailException extends RuntimeException {
    public MalformedMailException(String message) {
        super(message);
    }
}
