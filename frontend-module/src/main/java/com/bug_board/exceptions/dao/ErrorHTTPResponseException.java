package com.bug_board.exceptions.dao;

public class ErrorHTTPResponseException extends Exception {
    public ErrorHTTPResponseException(String message) {
        super(message);
    }
}
