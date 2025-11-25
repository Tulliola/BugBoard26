package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;

public interface IUserService {
    public UserSummaryDTO registerNewUser(UserCreationDTO user);
}
