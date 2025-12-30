package com.bug_board.backendmodule.exception.security;

public class NoKeyGeneratorAlgorithmFoundException extends RuntimeException {
    public NoKeyGeneratorAlgorithmFoundException(String message) {
        super(message);
    }
}
