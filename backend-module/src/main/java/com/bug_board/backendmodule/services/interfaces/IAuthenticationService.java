package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.enum_classes.UserRole;

public interface IAuthenticationService {
   public abstract String verify(UserAuthenticationDTO userAuthenticationDTO);
   public UserRole getRole(String username);
}
