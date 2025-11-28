package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IIssueRepositoryJPA extends JpaRepository<Issue, Integer>{
    List<Issue> findAllByCreator_Username(String creatorUsername);
}
