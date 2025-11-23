package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements IUserService {
    @Override
    public void registerNewUser(UserCreationDTO user) {

    }
}
