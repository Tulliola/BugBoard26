package com.bug_board.backendmodule.repositories.interfaces;

import com.bug_board.backendmodule.entity.Issue;

import java.util.List;

public interface IIssueRepository {
    public List<Issue> retrieveAllUsersIssues(String username);
    public Issue createANewIssueToProject(Issue issueToPublish);
    public List<Issue> retrieveAllProjectsIssues(Integer idProject);
}
