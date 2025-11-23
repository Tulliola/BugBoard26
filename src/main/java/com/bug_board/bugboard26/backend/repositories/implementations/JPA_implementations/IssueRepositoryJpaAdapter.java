package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Issue;
import com.bug_board.bugboard26.backend.repositories.interfaces.IIssueRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRepositoryJpaAdapter implements IIssueRepository {
    private final IIssueRepositoryJPA repositoryJPA;

    public IssueRepositoryJpaAdapter(IIssueRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }

    @Override
    public List<Issue> retrieveAllUsersIssues(String username) {
        return repositoryJPA.findAll();
        //TODO aggiungere nuovo query method
    }

    @Override
    public void publishANewIssueToProject(Integer idProject, Issue issueToPublish) {
        repositoryJPA.save(issueToPublish);
    }

    @Override
    public List<Issue> retrieveAllProjectsIssues(Integer idProject) {
        //TODO aggiunere query method
        return null;
    }
}
