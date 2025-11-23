package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIssueRepositoryJPA extends JpaRepository<Issue, Integer>{
}
