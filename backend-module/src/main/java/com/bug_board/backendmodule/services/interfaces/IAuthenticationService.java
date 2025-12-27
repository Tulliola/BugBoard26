package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.enum_classes.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IAuthenticationService {
   public abstract String verify(@NotNull @Valid UserAuthenticationDTO userAuthenticationDTO);
   public UserRole getRole(String username);
}
