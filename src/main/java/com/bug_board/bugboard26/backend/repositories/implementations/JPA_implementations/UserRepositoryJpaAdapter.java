package com.bug_board.bugboard26.backend.repositories.implementations.JPA_implementations;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJpaAdapter implements IUserRepository {
    private final IUserRepositoryJPA userRepositoryJPA;



    public UserRepositoryJpaAdapter(IUserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }


    @Override
    public User registerNewUser(User userToRegister) {
        return null;
    }
}
