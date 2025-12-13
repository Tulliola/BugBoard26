package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssueState {
    TODO("To-do", "#A3A3A3"),
    ASSIGNED("Assigned", "#FFBD2E"),
    SOLVED("Solved", "#27C93F");

    private final String state;
    private final String color;

    IssueState(String state, String color)
    {
        this.state = state;
        this.color = color;
    }

    @JsonValue
    public String getState(){
        return state;
    }

    public String getColor(){
        return color;
    }

    @Override
    public String toString(){
        return this.state;
    }
}
