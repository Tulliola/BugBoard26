package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Issue;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;

import java.util.List;

public class IssueRepositoryJPA implements IIssueRepository {
    @Override
    public List<Issue> retrieveAllUsersIssues(String username) {
        return List.of();
    }

    @Override
    public void publishANewIssueToProject(Integer idProject, Issue issueToPublish) {

    }

    @Override
    public List<Issue> retrieveAllProjectsIssues(Integer idProject) {
        return List.of();
    }
}
