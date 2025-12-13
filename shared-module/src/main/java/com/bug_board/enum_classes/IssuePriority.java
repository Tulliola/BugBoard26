package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum IssuePriority {
    NO_PRIORITY("No priority", null),
    LOW_PRIORITY("Low priority", "/shared_module/images/low_priority.png"),
    MEDIUM_PRIORITY("Medium priority", "/shared_module/images/medium_priority.png"),
    HIGH_PRIORITY("High priority", "/shared_module/images/high_priority.png");

    private final String priority;
    private final String imageURL;

    IssuePriority(String priority, String imageURL) {
        this.imageURL = imageURL;
        this.priority = priority;
    }

    public byte[] getAssociatedImage() {
        if(this.imageURL == null)
            return null;

        try {
            return IssueTipology.class.getResourceAsStream(this.imageURL).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonValue
    public String getPriority(){
        return priority;
    }

    @Override
    public String toString(){
        return this.priority;
    }
}
