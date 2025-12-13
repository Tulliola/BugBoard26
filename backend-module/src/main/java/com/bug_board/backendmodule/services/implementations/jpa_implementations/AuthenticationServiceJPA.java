package com.bug_board.backendmodule.services.implementations.jpa_implementations;

import com.bug_board.backendmodule.repositories.interfaces.IUserRepository;
import com.bug_board.backendmodule.security.JWTService;
import com.bug_board.backendmodule.services.interfaces.IAuthenticationService;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.enum_classes.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceJPA implements IAuthenticationService {

    private final JWTService jwtService;
    private final IUserRepository  userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceJPA(JWTService jwtService,
                                    IUserRepository userRepository,
                                    AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String verify(UserAuthenticationDTO userAuthenticationDTO) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthenticationDTO.getUsername(),
                        userAuthenticationDTO.getPassword())
                );

        return jwtService.generateToken(userAuthenticationDTO.getUsername());
    }

    @Override
    public UserRole getRole(String username) {
        return userRepository.findUserByUsername(username).getRole();
    }
}
