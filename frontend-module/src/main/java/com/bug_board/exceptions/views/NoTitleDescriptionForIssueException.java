package com.bug_board.exceptions.views;

public class NoTitleDescriptionForIssueException extends RuntimeException {
    public NoTitleDescriptionForIssueException(String message) {
        super(message);
    }
}
