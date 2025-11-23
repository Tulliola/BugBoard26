package com.bug_board.bugboard26.backend.entity;

import com.bug_board.bugboard26.enum_classes.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private String username;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "pwd", nullable = false)
    private String password;

    @Column(name = "ruolo", columnDefinition = "ruoloenum not null")
    private UserRole role;

    @Column(name = "bioPic")
    private byte[] bioPic;

}