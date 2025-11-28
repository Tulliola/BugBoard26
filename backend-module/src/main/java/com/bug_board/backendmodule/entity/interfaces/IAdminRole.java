package com.bug_board.backendmodule.entity.interfaces;

import com.bug_board.backendmodule.entity.Project;

public interface IAdminRole {
    public abstract void addProjectToOverviewedProjectList(Project project);
}
