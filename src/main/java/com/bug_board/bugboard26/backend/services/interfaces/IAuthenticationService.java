package com.bug_board.bugboard26.backend.services.interfaces;

import com.bug_board.bugboard26.dto.UserAuthenticationDTO;
import org.springframework.stereotype.Service;

public interface IAuthenticationService {
   public abstract String verify(UserAuthenticationDTO userAuthenticationDTO);
}
