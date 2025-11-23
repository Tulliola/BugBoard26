package backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "utente")
public class User {
    @Id
    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "username", nullable = false, length = 25)
    private String username;

    //Relazione con label
    @OneToMany
    @JoinColumn(name = "utente_creatore")
    private List<Label> etichettas = new ArrayList<>();

    //Relazione regularuser - progetto
    @ManyToMany(mappedBy = "partecipants")
    private List<Project> progettiACuiPartecipo = new ArrayList<>();

    //Relazione admin - progetto
    @OneToMany(mappedBy = "utenteCreatore")
    private List<Project> progettiCreati = new ArrayList<>();

    //Relazione superadmin - progetto
    @ManyToMany(mappedBy = "admins")
    private List<Project> progettiSupervisionati = new ArrayList<>();

    //Relazione regularuser - issue
    @OneToMany(mappedBy = "utenteCreatore")
    private List<Issue> personalIssues = new ArrayList<Issue>();

/*
 TODO [Reverse Engineering] create field to map the 'ruolo' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "ruolo", columnDefinition = "ruoloenum not null")
    private Object ruolo;
*/
}