package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import com.bug_board.enum_classes.UserRole;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryJpaAdapter implements IUserRepository {
    private final IUserRepositoryJPA repositoryJPA;

    public UserRepositoryJpaAdapter(IUserRepositoryJPA repositoryJPA) {
        this.repositoryJPA = repositoryJPA;
    }
    @Override
    public User registerNewUser(User userToRegister) {
        return repositoryJPA.save(userToRegister);
    }

    @Override
    public User findUserByUsername(String username) {
        return repositoryJPA.findByUsername(username);
    }

    @Override
    public boolean existsByEmailAndRole(String email, UserRole role) {
        return repositoryJPA.existsByEmailAndRole(email, role);
    }

    @Override
    public List<User> findAllUsers(){
        return repositoryJPA.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }
}
