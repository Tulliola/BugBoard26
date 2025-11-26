package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.UserAuthenticationDTO;

public interface IAuthenticationService {
   public abstract String verify(UserAuthenticationDTO userAuthenticationDTO);
}
