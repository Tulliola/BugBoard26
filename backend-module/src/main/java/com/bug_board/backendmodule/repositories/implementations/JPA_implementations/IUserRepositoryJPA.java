package com.bug_board.backendmodule.repositories.implementations.JPA_implementations;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.enum_classes.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepositoryJPA extends JpaRepository<User, String> {
    public User findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmailAndRole(String email, UserRole role);
}
