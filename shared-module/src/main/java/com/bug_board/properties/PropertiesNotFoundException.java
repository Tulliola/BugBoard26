package com.bug_board.properties;

public class PropertiesNotFoundException extends RuntimeException {
    public PropertiesNotFoundException(String message) {
        super(message);
    }
}
