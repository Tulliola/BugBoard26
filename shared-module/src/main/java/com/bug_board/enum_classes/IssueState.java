package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssueState {
    Todo("To-do"),
    Working_on("Working on"),
    Solved("Solved");

    public final String state;

    IssueState(String state){
        this.state = state;
    }

    @JsonValue
    public String getState(){
        return state;
    }

    @Override
    public String toString(){
        return this.state;
    }
}
