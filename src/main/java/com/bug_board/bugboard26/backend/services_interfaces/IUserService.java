package com.bug_board.bugboard26.backend.services_interfaces;

import com.bug_board.bugboard26.dto.UserCreationDTO;
import org.springframework.stereotype.Service;

public interface IUserService {
    public void registerNewUser(UserCreationDTO user);
}
