package com.bug_board.bugboard26.enum_classes;

public enum ProjectTipology {
    Bug("Bug"),
    NewFeature("NewFeature"),
    Question("Question"),
    Documentation("Documentation");

    private final String tipology;

    ProjectTipology(String tipology){
        this.tipology = tipology;
    }

    public String getProjectType() {
        return tipology;
    }
}
