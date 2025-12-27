package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.backendmodule.entity.User;
import com.bug_board.dto.UserCreationDTO;
import com.bug_board.dto.UserSummaryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IUserService {
    public UserSummaryDTO registerNewUser(@NotNull @Valid UserCreationDTO user);
    public User getUser(@NotNull @NotBlank String username);
    public List<UserSummaryDTO> getAddableUsersToProject(@NotNull @Min(1) Integer idProject);
    public User findUserByUsername(@NotNull @NotBlank String collaboratorUsername);
}
