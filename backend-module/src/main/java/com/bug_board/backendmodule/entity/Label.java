package com.bug_board.backendmodule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "etichetta")
public class Label implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlabel", nullable = false)
    private Integer idLabel;

    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @Column(name = "descrizione", length = 200)
    private String description;

    @ColumnDefault("'#FFFFFF'")
    @Column(name = "colore", nullable = false, length = 7)
    private String color;

    @ManyToOne
    @JoinColumn(name = "utente_creatore")
    private User creator;

    public String getCreatorUsername(){
        return creator == null ?  null : creator.getUsername();
    }
}