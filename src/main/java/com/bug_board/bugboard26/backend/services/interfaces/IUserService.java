package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.backend.entity.User;
import com.bug_board.bugboard26.dto.UserAuthenticationDTO;
import com.bug_board.bugboard26.dto.UserCreationDTO;
import com.bug_board.bugboard26.dto.UserSummaryDTO;

import java.util.List;

public interface IUserService {
    public UserSummaryDTO registerNewUser(UserCreationDTO user);
    public User getUser(String username);
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject);
    public User findUserByUsername(String collaboratorUsername);
}
