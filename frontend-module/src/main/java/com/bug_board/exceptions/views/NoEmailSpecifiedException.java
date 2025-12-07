package com.bug_board.exceptions.views;

public class NoEmailSpecifiedException extends RuntimeException {
    public NoEmailSpecifiedException(String message) {
        super(message);
    }
}
