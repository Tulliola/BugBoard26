package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.User;

public interface IUserRepository {
    public User registerNewUser(User userToRegister);
   // public String authenticateUser(AuthenticationRequest authenticationRequest);
}
