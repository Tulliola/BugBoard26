package com.bug_board.bugboard26.backend.entity;

import com.bug_board.bugboard26.exception.MaximumLabelsException;

public interface IRegularUserRole {
    public abstract void addIssueToIssueList(Issue newIssue);
    public abstract  void addLabelToPersonalLabelList(Label newLabel) throws MaximumLabelsException;
    public abstract void removeLabelFromPersonalLabelList(Label labelToRemove);
}
