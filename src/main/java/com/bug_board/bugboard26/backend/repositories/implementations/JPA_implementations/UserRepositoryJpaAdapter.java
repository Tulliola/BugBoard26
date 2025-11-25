package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJpaAdapter implements IUserRepository {
    private final IUserRepositoryJPA repositoryJPA;

    private PasswordEncoder passwordEncoder;

    public UserRepositoryJpaAdapter(IUserRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }
    @Override
    public User registerNewUser(User userToRegister) {
        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        return repositoryJPA.save(userToRegister);
    }

    @Override
    public User findUserByUsername(String username) {
        return repositoryJPA.findByUsername(username);
    }
}
