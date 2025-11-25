package com.bug_board.bugboard26.backend.services.implementations.JPA_implementation;

import com.bug_board.bugboard26.backend.security.JWTService;
import com.bug_board.bugboard26.backend.services.interfaces.IAuthenticationService;
import com.bug_board.bugboard26.backend.services.interfaces.IUserService;
import com.bug_board.bugboard26.dto.UserAuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceJPA implements IAuthenticationService {
    private final IUserService userService;
    private final JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationServiceJPA(IUserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public String verify(UserAuthenticationDTO userAuthenticationDTO) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthenticationDTO.getUsername(),
                        userAuthenticationDTO.getPassword())
                );

        if(authentication.isAuthenticated())
            return jwtService.generateToken(userAuthenticationDTO.getUsername());

        return "Failed";
    }
}
