package com.bug_board.bugboard26.enum_classes;

public enum IssueState {
    Todo("To-do"),
    Working_on("Working on"),
    Solved("Solved");

    public final String state;

    IssueState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
