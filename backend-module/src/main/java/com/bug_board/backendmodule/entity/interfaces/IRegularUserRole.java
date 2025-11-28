package com.bug_board.backendmodule.entity.interfaces;

import com.bug_board.backendmodule.entity.Issue;
import com.bug_board.backendmodule.entity.Label;
import com.bug_board.backendmodule.entity.Project;
import com.bug_board.backendmodule.exception.entity.MaximumLabelsException;

public interface IRegularUserRole {
    public abstract void addIssueToIssueList(Issue newIssue);
    public abstract  void addLabelToPersonalLabelList(Label newLabel) throws MaximumLabelsException;
    public abstract void removeLabelFromPersonalLabelList(Label labelToRemove);
    public abstract void addProjectToPartecipatingProjectList(Project project);
}
