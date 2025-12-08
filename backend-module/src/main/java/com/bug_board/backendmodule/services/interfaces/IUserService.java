package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.dto.EmailToSendDTO;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;

import java.util.List;

public interface IUserService {
    public UserSummaryDTO registerNewUser(UserCreationDTO user);
    public User getUser(String username);
    public List<UserSummaryDTO> getAddableUsersToProject(Integer idProject);
    public User findUserByUsername(String collaboratorUsername);
}
