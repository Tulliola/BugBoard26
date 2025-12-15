package com.bug_board.exceptions.views;

public class IssueImageTooLargeException extends RuntimeException {
    public IssueImageTooLargeException(String message) {
        super(message);
    }
}
