package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.backend.repositories.interfaces.IUserRepository;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.dto.UserAuthenticationDTO;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements IUserService {
    private final IUserRepository userRepository;

    public UserServiceJPA(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSummaryDTO registerNewUser(UserCreationDTO user) {
        return null;
    }
}
