package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.Issue;

import java.util.List;

public interface IIssueRepository {
    public List<Issue> retrieveAllUsersIssues(String username);
    public void publishANewIssueToProject(Integer idProject, Issue issueToPublish);
    public List<Issue> retrieveAllProjectsIssues(Integer idProject);
}
