package com.bug_board.backendmodule.repositories.implementations.jpa_implementations;

import com.bug_board.backendmodule.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILabelRepositoryJPA extends JpaRepository<Label, Integer> {
    @Query(value = "SELECT * FROM Etichetta WHERE utente_creatore = ?1 OR utente_creatore IS null", nativeQuery = true)
    public List<Label> findAllByUsername(String username);
}
