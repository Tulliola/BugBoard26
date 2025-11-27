package com.bug_board.bugboard26.enum_classes;

public enum IssueTipology {
    Bug("Bug"),
    NewFeature("NewFeature"),
    Question("Question"),
    Documentation("Documentation");

    private final String tipology;

    IssueTipology(String tipology){
        this.tipology = tipology;
    }

    public String getProjectType() {
        return tipology;
    }

    @Override
    public String toString() {
        return this.tipology;
    }
}
