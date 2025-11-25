package com.bug_board.bugboard26.backend.entity;

import com.bug_board.bugboard26.enum_classes.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "utente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "ruolo",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class User {
    /* User specific attributes */
    @Id
    @Column(name = "username", nullable = false, length = 25)
    protected String username;

    @Column(name = "email", nullable = false, length = 40)
    protected String email;

    @Column(name = "pwd", nullable = false)
    protected String password;

    @Column(name = "ruolo", columnDefinition = "ruoloenum not null", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected UserRole role;

    @Column(name = "biopic")
    protected byte[] bioPic;

    protected User(){

    }

    protected User(UserRole role){
        this.role = role;
    }

    public abstract Collection<String> getRoleNames();

}