package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.enum_classes.UserRole;

import java.util.List;

public interface IUserRepository {
    public User registerNewUser(User userToRegister);
    public User findUserByUsername(String username);
    public boolean existsByEmailAndRole(String email, UserRole role);
    public List<User> findAllUsers();
}
