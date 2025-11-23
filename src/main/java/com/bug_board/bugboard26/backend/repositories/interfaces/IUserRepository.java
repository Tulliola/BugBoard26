package com.bug_board.bugboard26.backend.repositories.interfaces;

import com.bug_board.bugboard26.backend.entity.User;

public interface IUserRepository {
    public void registerNewUser(User userToRegister);
   // public String authenticateUser(AuthenticationRequest authenticationRequest);
}
