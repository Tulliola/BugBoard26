package com.bug_board.bugboard26.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "etichetta")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlabel", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 20)
    private String nome;

    @Column(name = "descrizione", length = 200)
    private String descrizione;

    @ColumnDefault("'#FFFFFF'")
    @Column(name = "colore", nullable = false, length = 7)
    private String colore;
}