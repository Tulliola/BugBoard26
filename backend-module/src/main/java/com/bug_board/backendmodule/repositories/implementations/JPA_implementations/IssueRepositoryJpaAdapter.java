package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.Issue;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRepositoryJpaAdapter implements IIssueRepository {
    private final IIssueRepositoryJPA issueRepositoryJPA;

    public IssueRepositoryJpaAdapter(IIssueRepositoryJPA issueRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
    }

    @Override
    public List<Issue> retrieveAllUsersIssues(String username) {
        return issueRepositoryJPA.findAllByCreator_Username((username));
    }

    @Override
    public Issue createANewIssueToProject(Issue issueToPublish) {
        return issueRepositoryJPA.save(issueToPublish);
    }

    @Override
    public List<Issue> retrieveAllProjectsIssues(Integer idProject) {
        //TODO aggiunere query method per la join
        return null;
    }
}
