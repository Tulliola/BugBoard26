package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssuePriority {
    No_priority("No priority"),
    Low_priority("Low priority"),
    Medium_priority("Medium priority"),
    High_priority("High priority");

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
