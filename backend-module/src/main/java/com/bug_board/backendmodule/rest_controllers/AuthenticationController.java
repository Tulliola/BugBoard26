package com.bug_board.backendmodule.rest_controllers;

import com.bug_board.backendmodule.services.interfaces.IAuthenticationService;
import com.bug_board.dto.LoginResponseDTO;
import com.bug_board.dto.UserAuthenticationDTO;
import com.bug_board.enum_classes.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IAuthenticationService authService;

    public AuthenticationController(IAuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody UserAuthenticationDTO userAuthentication) {
        String jwtToken = authService.verify(userAuthentication);

        UserRole role = authService.getRole(userAuthentication.getUsername());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(jwtToken);
        loginResponseDTO.setRole(role);
        loginResponseDTO.setUsername(userAuthentication.getUsername());

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

}
