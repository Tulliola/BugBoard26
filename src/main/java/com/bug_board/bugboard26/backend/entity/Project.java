package com.bug_board.bugboard26.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "progetto")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprogetto", nullable = false)
    private Integer id;

    @Column(name = "titolo", nullable = false, length = 50)
    private String titolo;

    @Column(name = "descrizione", nullable = false, length = Integer.MAX_VALUE)
    private String descrizione;

    @Column(name = "immagine")
    private byte[] immagine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_creatore", nullable = false)
    private User utenteCreatore;

    @OneToMany
    @JoinColumn(name = "utente_creatore", nullable = false)
    private Set<Issue> issues = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "supervisiona_progetto",
            joinColumns = @JoinColumn(name = "idProgetto"),
            inverseJoinColumns = @JoinColumn(name = "amministratore")
    )
    private List<User> admins = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "partecipa_a_progetto",
            joinColumns = @JoinColumn(name = "idProgetto"),
            inverseJoinColumns = @JoinColumn(name = "utente_partecipante")
    )
    private Set<User> partecipants = new LinkedHashSet<>();

}