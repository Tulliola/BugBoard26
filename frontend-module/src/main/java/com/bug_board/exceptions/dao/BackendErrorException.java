package com.bug_board.exceptions.dao;

public class BackendErrorException extends Exception {
    public BackendErrorException(String message) {
        super(message);
    }
}
