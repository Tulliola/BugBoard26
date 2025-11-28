package com.bug_board.backendmodule.repositories.interfaces;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.enum_classes.UserRole;

import java.util.List;

public interface IUserRepository {
    public User registerNewUser(User userToRegister);
    public User findUserByUsername(String username);
    public boolean existsByEmailAndRole(String email, UserRole role);
    public List<User> findAllUsers();
}
