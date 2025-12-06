package com.bug_board.exceptions.views;

public class CredentialsNotProvidedException extends RuntimeException {
    public CredentialsNotProvidedException(String message) {
        super(message);
    }
}
