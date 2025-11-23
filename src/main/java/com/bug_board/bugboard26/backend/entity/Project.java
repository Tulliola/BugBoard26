package com.bug_board.bugboard26.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "progetto")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprogetto", nullable = false)
    private Integer idProject;

    @Column(name = "titolo", nullable = false, length = 50)
    private String title;

    @Column(name = "descrizione", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "immagine")
    private byte[] image;

    /* Relation Project - SuperAdmin */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_creatore", nullable = false)
    private User creator;

    /* Relation Project - Issue */
    @OneToMany
    @JoinColumn(name = "utente_creatore", nullable = false)
    private List<Issue> issues = new ArrayList<Issue>();

    /* Relation Project - Admin */
    @ManyToMany
    @JoinTable(
            name = "supervisiona_progetto",
            joinColumns = @JoinColumn(name = "idProgetto"),
            inverseJoinColumns = @JoinColumn(name = "amministratore")
    )
    private List<Admin> admins = new ArrayList<Admin>();

    /* Relation Project - RegularUser */
    @ManyToMany
    @JoinTable(
            name = "partecipa_a_progetto",
            joinColumns = @JoinColumn(name = "idProgetto"),
            inverseJoinColumns = @JoinColumn(name = "utente_partecipante")
    )
    private List<RegularUser> partecipants = new ArrayList<RegularUser>();

    public void addIssueToIssueList(Issue newIssue) {
        if(newIssue != null && issues != null)
            issues.add(newIssue);
    }

    public void addUserWorkingOnProject(RegularUser user) {
        if(user != null && partecipants != null)
            partecipants.add(user);
    }

    public void addAdminOverviewingProject(Admin admin) {
        if(admin != null && admins != null)
            admins.add(admin);
    }
}