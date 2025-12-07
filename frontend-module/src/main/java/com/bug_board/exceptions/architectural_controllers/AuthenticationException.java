package com.bug_board.exceptions.architectural_controllers;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
