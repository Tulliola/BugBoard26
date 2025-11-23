package com.bug_board.bugboard26.backend.entity.interfaces;

import com.bug_board.bugboard26.backend.entity.Project;

public interface IAdminRole {
    public abstract void addProjectToOverviewedProjectList(Project project);
}
