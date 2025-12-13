package com.bug_board.backendmodule.repositories.implementations.jpa_implementations;

import com.bug_board.backendmodule.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IIssueRepositoryJPA extends JpaRepository<Issue, Integer>, JpaSpecificationExecutor<Issue> {
}
