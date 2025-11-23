package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILabelRepositoryJPA extends JpaRepository<Label, Integer> {
    public List<Label> findAllByUsername(String username);
}
