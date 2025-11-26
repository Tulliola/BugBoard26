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
    private SuperAdmin creator;

    /* Relation Project - Issue */
    @OneToMany(mappedBy = "project")
    private List<Issue> issues = new ArrayList<Issue>();

    /* Relation Project - Admin */
    @ManyToMany
    @JoinTable(
            name = "supervisiona_progetto",
            joinColumns = @JoinColumn(name = "idprogetto"),
            inverseJoinColumns = @JoinColumn(name = "amministratore")
    )
    private List<Admin> admins = new ArrayList<Admin>();

    /* Relation Project - RegularUser */
    @ManyToMany
    @JoinTable(
            name = "partecipa_a_progetto",
            joinColumns = @JoinColumn(name = "idprogetto"),
            inverseJoinColumns = @JoinColumn(name = "utente_partecipante")
    )
    private List<RegularUser> partecipants = new ArrayList<RegularUser>();

    public Project() {}

    public Project(Admin admin, SuperAdmin superAdmin) {
        if(admin == null)
            throw new NullPointerException("You must define the first admin of the project.");

        if(superAdmin == null)
            throw new  NullPointerException("You must define the creator of the project.");

        admins.add(admin);
        admin.addProjectToOverviewedProjectList(this);

        creator = superAdmin;
        creator.addProjectToCreatedProjectList(this);
    }

    public void addIssueToIssueList(Issue newIssue) {
        if(issues != null) {
            if (newIssue == null)
                throw new NullPointerException("You must define the issue of the project.");

            issues.add(newIssue);
        }
    }

    public void addUserWorkingOnProject(RegularUser user) {
        if(partecipants != null) {
            if (user == null)
                throw new NullPointerException("You must define who is working on the project.");

            partecipants.add(user);
        }
    }

    public void addAdminOverviewingProject(Admin admin) {
        if(admins != null){
            if(admin == null)
                throw  new NullPointerException("You must define the admin of the project.");

            admins.add(admin);
        }
    }

    public boolean hasCollaborators(){
        return (admins != null && !admins.isEmpty()) &&
                (partecipants != null && !partecipants.isEmpty());
    }
}