package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.Project;
import com.bug_board.bugboard26.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProjectRepositoryJPA extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT email, pwd, username, ruolo, biopic FROM ( " +
                        "SELECT amministratore as username FROM Supervisiona_progetto WHERE idProgetto = ?1" +
                        " UNION " +
                        "SELECT utente_partecipante as username FROM Partecipa_a_progetto WHERE idProgetto = ?1" +
                    ") NATURAL JOIN Utente",
        nativeQuery = true
    )
    public List<User> getProjectMembers(Integer idProject);

    @Query(value = "SELECT titolo, descrizione, immagine, idprogetto, utente_creatore " +
            "FROM progetto NATURAL JOIN partecipa_a_progetto " +
            "WHERE utente_partecipante = ?1",
        nativeQuery = true)
    public List<Project> findAllByPartecipants_Username(String username);

    @Query(value = "SELECT titolo, descrizione, immagine, idProgetto, utente_creatore " +
            "FROM progetto NATURAL JOIN partecipa_a_progetto " +
            "WHERE utente_partecipante = ?1 AND titolo = ?2",
        nativeQuery = true)
    public List<Project> findAllByTitleAndPartecipants_Username(String username, String titleToFilter);

    @Query(value = "SELECT titolo, descrizione, immagine, idprogetto, utente_creatore " +
            "FROM progetto NATURAL JOIN supervisiona_progetto " +
            "WHERE amministratore = ?1",
            nativeQuery = true)
    public List<Project> findAllByAdmins_Username(String username);

    @Query(value = "SELECT titolo, descrizione, immagine, idProgetto, utente_creatore " +
            "FROM progetto NATURAL JOIN supervisiona_progetto " +
            "WHERE amministratore = ?1 AND titolo = ?2",
            nativeQuery = true)
    public List<Project> findAllByTitleAndAdmins_Username(String username, String titleToFilter);
}
