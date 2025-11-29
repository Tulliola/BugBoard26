package com.bug_board.dao.interfaces;

import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;

public interface IUserDAO {
    public UserSummaryDTO registerNewUser(UserCreationDTO userToRegister);
}
