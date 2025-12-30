package com.bug_board.enum_classes.exceptions;

public class NoSuchImageException extends RuntimeException {
    public NoSuchImageException(String message) {
        super(message);
    }
}
