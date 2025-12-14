package com.bug_board.exceptions.views;

public class NoTitleSpecifiedForIssueException extends RuntimeException {
    public NoTitleSpecifiedForIssueException(String message) {
        super(message);
    }
}
