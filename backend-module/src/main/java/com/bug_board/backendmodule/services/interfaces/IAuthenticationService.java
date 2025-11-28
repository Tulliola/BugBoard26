package com.bug_board.backendmodule.services.interfaces;

import com.bug_board.dto.UserAuthenticationDTO;

public interface IAuthenticationService {
   public abstract String verify(UserAuthenticationDTO userAuthenticationDTO);
}
