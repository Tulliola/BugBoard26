package com.bug_board.bugboard26.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idissue", nullable = false)
    private Integer id;

    @Column(name = "titolo", nullable = false, length = 40)
    private String titolo;

    @Column(name = "descrizione", nullable = false, length = Integer.MAX_VALUE)
    private String descrizione;

    @ManyToMany
    @JoinTable(
            name= "etichetta_associata",
            joinColumns = @JoinColumn(name = "idissue"),
            inverseJoinColumns = @JoinColumn(name = "idlabel")
    )
    private List<Label> etichetteAssociate = new ArrayList<Label>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_creatore")
    private User utenteCreatore;

/*
 TODO [Reverse Engineering] create field to map the 'stato' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'To-do'")
    @Column(name = "stato", columnDefinition = "statoissueenum not null")
    private Object stato;
*/
/*
 TODO [Reverse Engineering] create field to map the 'tipologia' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "tipologia", columnDefinition = "issueenum not null")
    private Object tipologia;
*/
}