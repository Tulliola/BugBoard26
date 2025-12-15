package com.bug_board.exceptions.views;

public class NoDescriptionForIssueException extends RuntimeException {
    public NoDescriptionForIssueException(String message) {
        super(message);
    }
}
