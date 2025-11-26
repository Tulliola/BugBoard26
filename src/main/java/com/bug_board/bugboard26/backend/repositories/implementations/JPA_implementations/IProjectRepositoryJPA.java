package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProjectRepositoryJPA extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT * FROM ( " +
                        "SELECT amministratore as username FROM Supervisiona_progetto WHERE idProgetto = ?1" +
                        " UNION " +
                        "SELECT utente_partecipante as username FROM Partecipa_a_progetto WHERE idProgetto = ?1" +
                    ") NATURAL JOIN Utente",
        nativeQuery = true
    )
    public List<User> getProjectMembers(Integer idProject);
}
