package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.UserCreationDTO;

public interface IUserService {
    public void registerNewUser(UserCreationDTO user);
}
