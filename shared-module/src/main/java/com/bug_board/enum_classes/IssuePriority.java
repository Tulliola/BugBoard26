package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssuePriority {
    NO_PRIORITY("No priority"),
    LOW_PRIORITY("Low priority"),
    MEDIUM_PRIORITY("Medium priority"),
    HIGH_PRIORITY("High priority");

    private final String priority;

    IssuePriority(String priority)
    {
        this.priority = priority;
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
