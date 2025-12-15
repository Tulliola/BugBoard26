package com.bug_board.backendmodule.repositories.interfaces;

import com.bug_board.backendmodule.entity.Issue;
import com.bug_board.dto.IssueFiltersDTO;

import java.util.List;

public interface IIssueRepository {
    public List<Issue> retrieveAllUsersIssues(String username, IssueFiltersDTO filters);
    public Issue createANewIssueToProject(Issue issueToPublish);
    public List<Issue> retrieveAllProjectsIssues(Integer idProject, IssueFiltersDTO filters);
    public Issue getIssue(Integer idIssue);
}
